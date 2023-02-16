package io.github.starbank.avaliador.application;

import feign.FeignException;
import io.github.starbank.avaliador.application.exception.DadosClienteNotFoundException;
import io.github.starbank.avaliador.application.exception.ErroComunicacaoMicroServicesException;
import io.github.starbank.avaliador.application.exception.SolicitacaoCartaoException;
import io.github.starbank.avaliador.application.representation.CartaoDto;
import io.github.starbank.avaliador.application.representation.ClienteDto;
import io.github.starbank.avaliador.domain.model.*;
import io.github.starbank.avaliador.infra.clients.CartoesResourceClient;
import io.github.starbank.avaliador.infra.clients.ClienteResourceClient;
import io.github.starbank.avaliador.infra.mqueue.SolicitacaoCartaoPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clientesClient;
    private final CartoesResourceClient cartoesClient;

    private final SolicitacaoCartaoPublisher solicitacaoCartaoPublisher;

    public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClienteNotFoundException, ErroComunicacaoMicroServicesException {
        try {
            var dadosCliente = obtemDadosCliente(cpf);
            var cartoesDoCliente = obtemCartoesDoCliente(cpf);

            return SituacaoCliente.builder()
                    .cliente(dadosCliente)
                    .cartoes(cartoesDoCliente)
                    .build();
        } catch (FeignException e){
            int status = e.status();
            if(status == HttpStatus.NOT_FOUND.value()){
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroServicesException(e.getMessage(), status);
        }
    }

    public List<CartaoDto> obtemCartoesAprovados(String cpf, Long renda) throws DadosClienteNotFoundException,
            ErroComunicacaoMicroServicesException{
        try {
            var cartoesDisponiveis = cartoesDisponiveisParaRendaAte(renda);
            var dadosCliente = obtemDadosCliente(cpf);

            return buscaCartoesParaLimiteAprovado(dadosCliente, cartoesDisponiveis);

        } catch (FeignException e){
            int status = e.status();
            if(status == HttpStatus.NOT_FOUND.value()){
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroServicesException(e.getMessage(), status);
        }
    }

    private ClienteDto obtemDadosCliente(String cpf){
        ResponseEntity<ClienteDto> respostaCliente = clientesClient.dadosCliente(cpf);
        return respostaCliente.getBody();
    }

    private List<CartaoDto> obtemCartoesDoCliente(String cpf){
        return cartoesClient.buscarCartoesPorCliente(cpf).getBody();
    }

    private List<Cartao> cartoesDisponiveisParaRendaAte(Long renda) {
        return cartoesClient.buscarCartoesComRendaAte(renda).getBody();
    }

    private List<CartaoDto> buscaCartoesParaLimiteAprovado(ClienteDto dadosCliente, List<Cartao> cartoesDisponiveis){
        return cartoesDisponiveis.stream().map(cartao -> {
            BigDecimal limiteBasico = cartao.getLimiteBasico();
            BigDecimal idade = BigDecimal.valueOf(dadosCliente.getIdade());

            var limiteAprovado = idade.divide(BigDecimal.valueOf(10)).multiply(limiteBasico);

            CartaoDto aprovado = new CartaoDto();
            aprovado.setNome(cartao.getNome());
            aprovado.setBandeira(cartao.getBandeiraCartao());
            aprovado.setLimiteBasico(limiteAprovado);
            return aprovado;
        }).collect(Collectors.toList());

    }

    public ProtocoloSolicitacaoCartao solicitarEmissaoCartao(DadosSolicitacaoEmissaoCartao dados){
        try {
            solicitacaoCartaoPublisher.solicitarCartao(dados);
            var protocolo = UUID.randomUUID().toString();
            return new ProtocoloSolicitacaoCartao(protocolo);
        } catch (Exception e) {
            throw new SolicitacaoCartaoException(e.getMessage());
        }
    }

}
