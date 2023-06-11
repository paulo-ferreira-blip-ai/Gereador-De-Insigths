package com.takeblip.sumarizacao.service.strategy.impl;

import com.takeblip.sumarizacao.enums.DescricaoSentimento;
import com.takeblip.sumarizacao.enums.LogEnum;
import com.takeblip.sumarizacao.model.Conversas;
import com.takeblip.sumarizacao.service.strategy.InsightsStrategy;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class InsightsStrategyImpl implements InsightsStrategy {

    private static final Logger LOGGER = Logger.getLogger(InsightsStrategyImpl.class.getName());


    //Contagem de ocorrências: É possível contar a quantidade de ocorrências de cada status (Pendente, Respondido,
    // Em Espera, Aberto, Fechado, Ignorado) e analisar a distribuição. Isso pode fornecer insights sobre a demanda de
    // atendimento, a eficiência na resolução de problemas e a proporção de interações que ficam sem resposta.
    @Override
    public Map<String, Integer> contagemDeOcorrencias(List<Conversas> conversas) {
        Map<String, Integer> ocorrencias = new HashMap<>();

        LOGGER.log(Level.INFO, LogEnum.INFO_INICIO_CONTAGEM_DE_OCORRENCIAS_STRATEGY.toString());

        for (Conversas conversa : conversas) {
            String status = conversa.getStatus();
            LOGGER.log(Level.INFO, "Processando conversa com status: {0}", status);


            if (status == null || status.isEmpty()) {
                continue; // Aqui pulamos a iteração atual e vai para a próxima conversa se o campos status estiver vazio ou nulo
            }


            //Aqui estamos verificando cada Objeto da lista de conversas enviada, percorremos cada status
            //verificamos se ele existe, se existir, atribuimos mais um na contagem para esse status
            if (ocorrencias.containsKey(status)) {
                int quantidade = ocorrencias.get(status);
                ocorrencias.put(status, quantidade + 1);
            } else {
                ocorrencias.put(status, 1);
            }
        }

        LOGGER.log(Level.INFO, LogEnum.INFO_FIM_CONTAGEM_DE_OCORRENCIAS_STRATEGY.toString() + " Resultado: {0}", ocorrencias);

        return ocorrencias;
    }


    //Análise de sentimento: Utilizando técnicas de processamento de linguagem natural, é possível classificar a
    // polaridade dos textos em positivo, negativo ou neutro. Isso permite analisar o sentimento predominante nos
    // comentários e mensagens, identificar padrões de satisfação ou insatisfação dos clientes e acompanhar a evolução
    // do sentimento ao longo do tempo.
    @Override
    public double analiseDeSentimentosStrategy(List<String[]> data) {
        LOGGER.log(Level.INFO, LogEnum.INFO_INICIO_ANALISE_DE_SENTIMENTOS_STRATEGY.toString());

        if (data == null || data.isEmpty()) {
            //estou usando logger pois uma exception
            LOGGER.log(Level.WARNING, "Lista de dados vazia ou nula. Não é possível realizar a análise de sentimentos.");
            return 0.0; // Ou qualquer valor padrão que você queira retornar nesses casos
        }

        //Aqui a classe StanfordCoreNLP esta usando o metodo new StanfordCoreNLP para buscar os arquivos
        //que fazem as analises das mensagens, esses arquivos se encontram no pacote resource, o método faz referencia
        //ao arquivo StanfordCoreNLP.properties onde foi configurado script com os parametros a serem analisados e os
        //caminhos dos arquivos para seus respectivos parametros
        StanfordCoreNLP esteiraDeAnaliseStanfordCoreNLP = new StanfordCoreNLP("StanfordCoreNLP.properties");

        //inicializando variaveis para posterior uso no calculo da media de sentimentos
        double somaDosSentimentos = 0;
        int numeroDeFrases = 0;

        for (String[] row : data) {
            //Aqui capturamos a 3ª coluna do objeto enviado
            String message = row[3]; // A terceira coluna contém a mensagem
            LOGGER.log(Level.INFO, "Retorno das mensagens: {0}", message);

            //Aqui armazenamos a frase na variavel anotações convertendo para o tipo Annotation
            //Esse método é responsável por executar uma série de etapas de processamento linguístico na mensagem, como
            //tokenização, segmentação de sentenças, análise morfológica, análise de dependência, entre outros.
            Annotation anotacoes = new Annotation(message);
            esteiraDeAnaliseStanfordCoreNLP.annotate(anotacoes);


            //Aqui temos um loop pra capturar as anotações de sentenças usando o CoreAnnotations.SentencesAnnotation.class
            //O CoreMap é uma interface do Stanford CoreNLP que representa uma unidade de texto anotada. No contexto do
            //processamento de linguagem natural, uma frase é uma unidade textual, mas o conceito de "frase" pode
            //variar dependendo do contexto e da definição adotada.
            for (CoreMap frase : anotacoes.get(CoreAnnotations.SentencesAnnotation.class)) {
                //Dentro do loop, a variável sentimento é obtida da frase usando o método
                // get(SentimentCoreAnnotations.SentimentClass.class). Esse método retorna a anotação de sentimento da
                // frase em questão.
                String sentimento = frase.get(SentimentCoreAnnotations.SentimentClass.class);
                //A classe SentimentCoreAnnotations.SentimentClass é uma classe do Stanford CoreNLP que representa a anotação
                //de sentimento de uma frase. Ela contém informações sobre o sentimento atribuído à frase, como
                // "Muito positivo", "Positivo", "Neutro", "Negativo" e "Muito negativo".
                double valorDoSentimento = converterSentimentoEmNumero(sentimento);
                //Em seguida, a variável sentimento é passada para o método converterSentimentoEmNumero(sentimento)
                //para obter o valor numérico correspondente ao sentimento. Esse método mapeia os valores de sentimento
                //para valores numéricos específicos, conforme definido no código.

                LOGGER.log(Level.INFO, "Sentimento: {0}, ValorDoSentimento: {1}", new Object[]{sentimento, valorDoSentimento});

                somaDosSentimentos += valorDoSentimento;
                numeroDeFrases++;
            }
        }

        double mediaDeSentimento = somaDosSentimentos / numeroDeFrases;

        LOGGER.log(Level.INFO, LogEnum.INFO_FIM_ANALISE_DE_SENTIMENTOS_STRATEGY.toString() + " Resultado: {0}", mediaDeSentimento);

        return mediaDeSentimento;
    }


    private double converterSentimentoEmNumero(String sentimento) {
        switch (sentimento) {
            case "Very positive":
                return DescricaoSentimento.MUITO_POSITIVO.getValor();
            case "Positive":
                return DescricaoSentimento.POSITIVO.getValor();
            case "Neutral":
                return DescricaoSentimento.NEUTRO.getValor();
            case "Negative":
                return DescricaoSentimento.NEGATIVO.getValor();
            case "Very negative":
                return DescricaoSentimento.MUITO_NEGATIVO.getValor();
            default:
                return DescricaoSentimento.NEUTRO.getValor();
        }
    }

}
