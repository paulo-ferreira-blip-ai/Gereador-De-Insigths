package com.takeblip.sumarizacao.controller;

import com.takeblip.sumarizacao.model.Conversas;
import com.takeblip.sumarizacao.service.LerArquivoCSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/takeblip")
public class ConversasController {

    @Autowired
    private LerArquivoCSV lerArquivoCSV;

   @GetMapping
    public ResponseEntity<List<Conversas>> buscarTodos(){
       try {
           return ResponseEntity.status(HttpStatus.OK).body(
                   lerArquivoCSV
                           .lerArquivoCSV("C:\\Users\\paulo\\OneDrive\\Documentos\\Desafio Tecnico\\sumarizacao\\sumarizacao\\src\\main\\java\\com\\takeblip\\sumarizacao\\configurations\\desafio-tecnico.csv"));
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
   }
}
