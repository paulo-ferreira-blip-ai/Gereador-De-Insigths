package com.takeblip.sumarizacao.exceptions;

public class InsightsException extends RuntimeException {
    public InsightsException(String message) {
        super(message);
    }

    public InsightsException(String message, Throwable cause) {
        super(message, cause);
    }
}
