package com.takeblip.sumarizacao.exceptions;

public class ConversaNotFoundException extends RuntimeException {
    public ConversaNotFoundException(String mensagem) {
        super(mensagem);
    }

    public ConversaNotFoundException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
