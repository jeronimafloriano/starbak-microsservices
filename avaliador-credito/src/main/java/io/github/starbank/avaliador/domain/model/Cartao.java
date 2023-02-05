package io.github.starbank.avaliador.domain.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Cartao {

    private Long id;

    private String nome;

    private String bandeiraCartao;

    private BigDecimal limiteBasico;
}
