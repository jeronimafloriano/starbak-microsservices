package io.github.starbank.cartoes.application.representation;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class EmissaoCartaoDto {
    private Long idCartao;
    private String cpf;
    private String endereco;
    private BigDecimal limiteLiberado;

}
