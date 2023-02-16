package io.github.starbank.avaliador.application.representation;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartaoDto {

    private String nome;
    private String bandeira;
    private BigDecimal limiteBasico;
}
