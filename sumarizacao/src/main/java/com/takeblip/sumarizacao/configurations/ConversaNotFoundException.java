package com.takeblip.sumarizacao.configurations;

public class ConversaNotFoundException extends RuntimeException {
    public ConversaNotFoundException(String mensagem) {
        super(mensagem);
    }

    public ConversaNotFoundException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
