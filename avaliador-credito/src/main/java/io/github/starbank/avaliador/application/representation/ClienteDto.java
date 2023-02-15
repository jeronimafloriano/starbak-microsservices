package io.github.starbank.avaliador.application.representation;

import lombok.Data;

@Data
public class ClienteDto {
    private String nome;
    private Integer idade;
    private String cpf;
    private Long renda;

}
