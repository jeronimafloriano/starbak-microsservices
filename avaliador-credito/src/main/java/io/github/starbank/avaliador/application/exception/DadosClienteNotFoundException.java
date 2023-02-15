package io.github.starbank.avaliador.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DadosClienteNotFoundException extends Exception{

    public DadosClienteNotFoundException(){
        super("Dados do cliente não encontrados para o CPF informado.");
    }
}
