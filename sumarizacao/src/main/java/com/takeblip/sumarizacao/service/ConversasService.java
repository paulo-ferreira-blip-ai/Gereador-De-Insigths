package com.takeblip.sumarizacao.service;

import com.takeblip.sumarizacao.model.Conversas;
import com.takeblip.sumarizacao.model.dto.ConversasDtoResponse;

import java.util.List;
import java.util.Map;

public interface ConversasService {
    //CRUD
    List<Conversas> buscarTodos();

    List<Conversas> buscarPorStatus(String status);

    List<Conversas> salvarDados(List<Conversas> conversas);

    Conversas alterarConversaPorId(String id, Map<String, Object> camposAtualizados);

    void deletarConversaPorId(String id);

    //Insights
    List<ConversasDtoResponse> contagemDeOcorrenciasService();

    Map<String, Object> analiseDeSentimentosService();


}
