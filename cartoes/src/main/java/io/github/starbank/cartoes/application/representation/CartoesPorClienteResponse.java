package io.github.starbank.cartoes.application.representation;

import io.github.starbank.cartoes.domain.ClienteCartao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartoesPorClienteResponse {

    private String nome;

    private String bandeiraCartao;

    private BigDecimal limiteBasico;

    public static CartoesPorClienteResponse fromModel(ClienteCartao model){
        return new CartoesPorClienteResponse(
                model.getCartao().getNome(),
                model.getCartao().getBandeiraCartao().toString(),
                model.getLimite()
        );
    }
}
