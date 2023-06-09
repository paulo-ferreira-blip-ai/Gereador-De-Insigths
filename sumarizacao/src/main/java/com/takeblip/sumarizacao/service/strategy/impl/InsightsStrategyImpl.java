package com.takeblip.sumarizacao.service.strategy.impl;

import com.takeblip.sumarizacao.model.Conversas;
import com.takeblip.sumarizacao.service.strategy.InsightsStrategy;
import com.takeblip.sumarizacao.service.strategy.LerArquivoCSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InsightsStrategyImpl implements InsightsStrategy {
    @Autowired
    private LerArquivoCSV lerArquivoCSV;


    //Contagem de ocorrências: É possível contar a quantidade de ocorrências de cada status (Pendente, Respondido,
    // Em Espera, Aberto, Fechado, Ignorado) e analisar a distribuição. Isso pode fornecer insights sobre a demanda de
    // atendimento, a eficiência na resolução de problemas e a proporção de interações que ficam sem resposta.
    @Override
    public Map<String, Integer> contagemDeOcorrencias(List<Conversas> conversas) {
        Map<String, Integer> ocorrencias = new HashMap<>();

        for (Conversas conversa : conversas) {
            String status = conversa.getStatus();

            // Verifica se o status já está presente no mapa
            if (ocorrencias.containsKey(status)) {
                int quantidade = ocorrencias.get(status);
                ocorrencias.put(status, quantidade + 1);
            } else {
                ocorrencias.put(status, 1);
            }
        }

        return ocorrencias;
    }

    //Análise de sentimento: Utilizando técnicas de processamento de linguagem natural, é possível classificar a
    // polaridade dos textos em positivo, negativo ou neutro. Isso permite analisar o sentimento predominante nos
    // comentários e mensagens, identificar padrões de satisfação ou insatisfação dos clientes e acompanhar a evolução
    // do sentimento ao longo do tempo.
    @Override
    public double analiseDeSentimentos(List<String[]> data) {
        return 0;
    }

    //Identificação de palavras-chave: Por meio da análise de palavras-chave recorrentes nas mensagens, é possível
    // identificar temas relevantes e tópicos de interesse dos clientes. Isso pode ajudar a compreender as principais
    // necessidades e expectativas dos clientes, identificar oportunidades de melhorias nos produtos/serviços e
    // desenvolver estratégias de marketing mais direcionadas.
    @Override
    public Map<String, Integer> identificarPalavrasChaves(List<String[]> data) {
        return null;
    }
}
