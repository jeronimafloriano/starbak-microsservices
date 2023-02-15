package io.github.starbank.cartoes.infra.mqueue;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.starbank.cartoes.domain.Cartao;
import io.github.starbank.cartoes.domain.ClienteCartao;
import io.github.starbank.cartoes.application.representation.EmissaoCartaoDto;
import io.github.starbank.cartoes.infra.repository.CartaoRepository;
import io.github.starbank.cartoes.infra.repository.ClienteCartaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmissaoCartaoSubscriber {

    private final CartaoRepository cartaoRepository;
    private final ClienteCartaoRepository clienteCartaoRepository;

    @RabbitListener(queues = "${mq.queues.emissao-cartoes}")
    public void receberSolicitacaoEmiteCartao(@Payload String payload){
        try {
            var mapper = new ObjectMapper();

            EmissaoCartaoDto dados = mapper.readValue(payload, EmissaoCartaoDto.class);
            Cartao cartao = cartaoRepository.findById(dados.getIdCartao()).orElseThrow();

            ClienteCartao clienteCartao = new ClienteCartao();
            clienteCartao.setCartao(cartao);
            clienteCartao.setCpf(dados.getCpf());
            clienteCartao.setLimite(dados.getLimiteLiberado());

            clienteCartaoRepository.save(clienteCartao);

        }catch (Exception e){
            log.error("Erro ao receber solicitacao de emissao de cartao: {} ", e.getMessage());
        }
    }
}