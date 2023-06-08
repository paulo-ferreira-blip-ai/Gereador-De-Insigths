package com.takeblip.sumarizacao.controller;

import com.takeblip.sumarizacao.model.Conversas;
import com.takeblip.sumarizacao.service.strategy.LerArquivoCSV;
import com.takeblip.sumarizacao.service.impl.ConversasServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/takeblip")
public class ConversasController {
    @Autowired
    private ConversasServiceImpl conversasService;

    @Autowired
    private LerArquivoCSV lerArquivoCSV;

    @GetMapping
    public ResponseEntity<List<Conversas>> buscarTodos() {
        return ResponseEntity.status(HttpStatus.OK).body(conversasService.buscarTodos());
    }

    @PostMapping(path = "/carregar-arquivo-csv")
    public void carregarArquivo(@RequestParam("file") MultipartFile file) throws IOException {
        List<Conversas> dados = lerArquivoCSV.lerArquivoCSV(file);
        ResponseEntity.status(HttpStatus.CREATED).body(conversasService.salvarDados(dados));
    }
}
