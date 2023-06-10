package com.takeblip.sumarizacao.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class ExceptionHandlerConfig {

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException ex) {
        // Lógica de tratamento da exceção IOException
        String mensagemErro = "Ocorreu um erro ao processar o arquivo: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensagemErro);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        // Lógica de tratamento da exceção IllegalArgumentException
        String mensagem = ex.getMessage();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(mensagem);
    }

    @ExceptionHandler(InsightsException.class)
    public ResponseEntity<String> handleInsightsException(Exception ex) {
        // Lógica de tratamento da exceção InsightsException
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro na geração de insights");
    }

    @ExceptionHandler(DataProcessingException.class)
    public ResponseEntity<String> handleDataProcessingException(Exception ex) {
        // Lógica de tratamento da exceção DataProcessingException
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro no processamento de dados");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        // Lógica de tratamento da exceção RuntimeException
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno");
    }
}
