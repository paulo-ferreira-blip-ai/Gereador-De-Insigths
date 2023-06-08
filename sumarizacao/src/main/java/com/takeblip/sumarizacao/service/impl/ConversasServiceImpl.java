package com.takeblip.sumarizacao.service.impl;

import com.takeblip.sumarizacao.model.Conversas;
import com.takeblip.sumarizacao.repository.ConversasRepository;
import com.takeblip.sumarizacao.service.ConversasService;
import com.takeblip.sumarizacao.service.strategy.LerArquivoCSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConversasServiceImpl implements ConversasService {
    @Autowired
    private ConversasRepository conversasRepository;

    @Autowired
    private LerArquivoCSV lerArquivoCSV;


    @Override
    public List<Conversas> buscarTodos() {
        return conversasRepository.findAll();
    }

    @Override
    public Conversas buscarPorCodigo(String codigo) {
        return null;
    }

    @Override
    public List<Conversas> salvarDados(List<Conversas> conversas) {
        //estamos iterando sobre a lista de conversas, salvando cada conversa individualmente usando o método save do
        // ConversasRepository e adicionando o documento salvo na lista savedConversas. Por fim, retornamos a lista de
        // documentos salvos.
        List<Conversas> savedConversas = new ArrayList<>();
        for (Conversas conversa : conversas) {
            savedConversas.add(conversasRepository.save(conversa));
        }
        return savedConversas;
    }
}
