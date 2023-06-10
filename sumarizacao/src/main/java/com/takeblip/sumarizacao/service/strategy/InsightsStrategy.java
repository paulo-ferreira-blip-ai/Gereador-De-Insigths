package com.takeblip.sumarizacao.service.strategy;

import com.takeblip.sumarizacao.model.Conversas;

import java.util.List;
import java.util.Map;

public interface InsightsStrategy {
    Map<String, Integer> contagemDeOcorrencias(List<Conversas> conversas);

    double analiseDeSentimentosStrategy(List<String[]> data);

}
