package io.github.starbank.clientes.application.representation;

import io.github.starbank.clientes.domain.Cliente;
import lombok.Data;

@Data
public class ClienteDto {
    private String cpf;
    private String nome;
    private Integer idade;

    public Cliente converter(){
        return new Cliente(cpf, nome, idade);
    }

}
