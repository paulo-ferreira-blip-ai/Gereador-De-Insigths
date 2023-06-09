package com.takeblip.sumarizacao.controller;

import com.takeblip.sumarizacao.configurations.ConversaNotFoundException;
import com.takeblip.sumarizacao.model.Conversas;
import com.takeblip.sumarizacao.model.dto.ConversasDtoResponse;
import com.takeblip.sumarizacao.service.impl.ConversasServiceImpl;
import com.takeblip.sumarizacao.service.strategy.LerArquivoCSV;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/takeblip")
@Tag(name = "CRUD & Insights", description = "Endpoints")
public class ConversasController {
    @Autowired
    private ConversasServiceImpl conversasService;

    @Autowired
    private LerArquivoCSV lerArquivoCSV;
    //CRUDs

    @GetMapping
    public ResponseEntity<List<Conversas>> buscarTodos() {
        return ResponseEntity.ok(conversasService.buscarTodos());
    }

    @GetMapping(path = "/conversas/{status}")
    public ResponseEntity<List<Conversas>> buscarPorStatus(@PathVariable String status) {
        try {
            return ResponseEntity.ok(conversasService.findByStatus(status));
        } catch (ConversaNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping(path = "/carregar-arquivo-csv")
    public void carregarArquivo(@RequestParam("file") MultipartFile file) throws IOException {
        List<Conversas> dados = lerArquivoCSV.lerArquivoCSV(file);
        ResponseEntity.ok(conversasService.salvarDados(dados));
    }


    @PatchMapping("/conversas/{id}")
    public ResponseEntity<Conversas> atualizarConversaPorId(@PathVariable String id, @RequestBody Map<String, Object> camposAtualizados) {
        try {
            Conversas conversaAtualizada = conversasService.alterarConversaPorId(id, camposAtualizados);
            return ResponseEntity.ok(conversaAtualizada);
        } catch (ConversaNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/conversas/{id}")
    public ResponseEntity<Void> deletarConversaPorId(@PathVariable String id) {
        try {
            conversasService.deletarConversaPorId(id);
            return ResponseEntity.noContent().build();
        } catch (ConversaNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //Insights
    @GetMapping(path = "/contagem-ocorrencias")
    public ResponseEntity<List<ConversasDtoResponse>> contagemDeOcorrencias() {
        return ResponseEntity.ok(conversasService.contagemDeOcorrencias());
    }


}
