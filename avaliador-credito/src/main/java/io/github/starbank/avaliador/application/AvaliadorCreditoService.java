package io.github.starbank.avaliador.application;

import feign.FeignException;
import io.github.starbank.avaliador.application.exception.DadosClienteNotFoundException;
import io.github.starbank.avaliador.application.exception.ErroComunicacaoMicroServicesException;
import io.github.starbank.avaliador.domain.model.*;
import io.github.starbank.avaliador.infra.clients.CartoesResourceClient;
import io.github.starbank.avaliador.infra.clients.ClienteResourceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clientesClient;
    private final CartoesResourceClient cartoesClient;

    public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClienteNotFoundException, ErroComunicacaoMicroServicesException {
        try {
            ResponseEntity<DadosCliente> responstaCliente = clientesClient.dadosCliente(cpf);
            ResponseEntity<List<CartaoCliente>> respostaCartoes = cartoesClient.buscarCartoesPorCliente(cpf);

            return SituacaoCliente.builder()
                    .cliente(responstaCliente.getBody())
                    .cartoes(respostaCartoes.getBody())
                    .build();
        } catch (FeignException e){
            int status = e.status();
            if(status == HttpStatus.NOT_FOUND.value()){
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroServicesException(e.getMessage(), status);
        }
    }

    public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda) throws DadosClienteNotFoundException,
            ErroComunicacaoMicroServicesException{
        try {
            ResponseEntity<DadosCliente> respostaCliente = clientesClient.dadosCliente(cpf);
            ResponseEntity<List<Cartao>> respostaCartoes = cartoesClient.buscarCartoesComRendaAte(renda);

            var cartoes = respostaCartoes.getBody();

            var cartoesAprovados = cartoes.stream().map(cartao -> {
                        BigDecimal limiteBasico = cartao.getLimiteBasico();
                        BigDecimal rendaCliente = BigDecimal.valueOf(renda);
                        BigDecimal idade = BigDecimal.valueOf(respostaCliente.getBody().getIdade());

                        var limiteAprovado = idade.divide(BigDecimal.valueOf(10)).multiply(limiteBasico);

                        CartaoAprovado aprovado = new CartaoAprovado();
                        aprovado.setCartao(cartao.getNome());
                        aprovado.setBandeira(cartao.getBandeiraCartao());
                        aprovado.setLimiteAprovado(limiteAprovado);
                        return aprovado;
                    }).collect(Collectors.toList());

            return new RetornoAvaliacaoCliente(cartoesAprovados);

        } catch (FeignException e){
            int status = e.status();
            if(status == HttpStatus.NOT_FOUND.value()){
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroServicesException(e.getMessage(), status);
        }
    }

}
