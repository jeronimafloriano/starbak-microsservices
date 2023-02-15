package io.github.starbank.avaliador.application.exception;

import lombok.Getter;


public class ErroComunicacaoMicroServicesException extends Exception{
    @Getter
    private final Integer status;
    public ErroComunicacaoMicroServicesException(String message, Integer status) {
        super("Ocorreu um erro na tentativa de busca dos dados do cliente." + message +  status);
        this.status = status;
    }
}
