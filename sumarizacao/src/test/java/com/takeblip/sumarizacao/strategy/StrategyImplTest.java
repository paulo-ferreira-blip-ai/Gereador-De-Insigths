package com.takeblip.sumarizacao.strategy;

import com.takeblip.sumarizacao.model.Conversas;
import com.takeblip.sumarizacao.repository.ConversasRepository;
import com.takeblip.sumarizacao.service.impl.ConversasServiceImpl;
import com.takeblip.sumarizacao.service.strategy.InsightsStrategy;
import com.takeblip.sumarizacao.service.strategy.impl.InsightsStrategyImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@ExtendWith(MockitoExtension.class)
public class StrategyImplTest {
    @InjectMocks
    private InsightsStrategyImpl insightsStrategyImpl;

    @Mock
    private InsightsStrategy insightsStrategy;

    @Mock
    private ConversasRepository conversasRepository;

    @Mock
    private ConversasServiceImpl conversasService;

    @Test
    public void testContagemDeOcorrenciasListaVaziaRetornaMapaVazio() {
        // Chamar o método contagemDeOcorrencias com uma lista vazia
        Map<String, Integer> resultado = insightsStrategyImpl.contagemDeOcorrencias(Collections.emptyList());

        // Verificar se o resultado é um mapa vazio
        Assert.assertTrue(resultado.isEmpty());
    }

    @Test
    public void testContagemDeOcorrencias_ContaCorretamente() {
        Map<String, Integer> ocorrencias = new HashMap<>();


        // Criação da lista de conversas com vários registros
        List<Conversas> conversas = new ArrayList<>();
        conversas.add(new Conversas("", "", "status1", ""));
        conversas.add(new Conversas("", "", "status2", ""));
        conversas.add(new Conversas("", "", "status1", ""));
        conversas.add(new Conversas("", "", "status3", ""));
        conversas.add(new Conversas("", "", "status2", ""));


        Map<String, Integer> resultado = insightsStrategyImpl.contagemDeOcorrencias(conversas);

        // Verificar as contagens esperadas
        assertEquals(2, resultado.get("status1").intValue());
        assertEquals(2, resultado.get("status2").intValue());
        assertEquals(1, resultado.get("status3").intValue());
    }

    @Test
    public void testContagemDeOcorrenciasAlgunsStatusAusentesOuNulos() {
        List<Conversas> conversas = new ArrayList<>();
        conversas.add(new Conversas("", "", "status1", ""));
        conversas.add(new Conversas("", "", "", "")); // Status ausente
        conversas.add(new Conversas("", "", "", null)); // Status nulo

        // Chamar o método contagemDeOcorrencias
        Map<String, Integer> resultado = insightsStrategyImpl.contagemDeOcorrencias(conversas);

        // Verificar as contagens esperadas
        assertFalse(resultado.containsKey("")); // Verificar que o status ausente não está presente no resultado
        assertFalse(resultado.containsKey(null)); // Verificar que o status nulo não está presente no resultado
    }


    @Test
    public void testDeveRetornarZeroQuandoAListaDeDadosVazia() {
        List<String[]> data = new ArrayList<>(Collections.emptyList());

        double resultado = insightsStrategyImpl.analiseDeSentimentosStrategy(data);

        assertEquals(0.0, resultado, 0.0001); // delta é a tolerância apropriada para a comparação de double

    }

    @Test
    public void testParaArquivosCsvComMensagensNeutras() {

        List<Conversas> mediano = new ArrayList<>();
        mediano.add(new Conversas("", "", "status1", "Just a regular inquiry. Need some information."));
        mediano.add(new Conversas("", "", "status2", "Not bad, but not great either. Average service"));

        List<String[]> data = new ArrayList<>();

        for (Conversas conversa : mediano) {
            String[] row = new String[4];
            row[0] = conversa.getId();
            row[1] = conversa.getData();
            row[2] = conversa.getStatus();
            row[3] = conversa.getMensagem();
            data.add(row);

            double resultado = insightsStrategyImpl.analiseDeSentimentosStrategy(data);

            assertEquals(0.438, resultado, 0.439);

        }
    }


    @Test
    public void testParaArquivosCsvComMensagensPositivas() {

        List<Conversas> positivo = new ArrayList<>();
        positivo.add(new Conversas("", "", "status1", "Great job! I'm really impressed."));
        positivo.add(new Conversas("", "", "status2", "This company is fantastic. I love their products"));

        List<String[]> data = new ArrayList<>();

        for (Conversas conversa : positivo) {
            String[] row = new String[4];
            row[0] = conversa.getId();
            row[1] = conversa.getData();
            row[2] = conversa.getStatus();
            row[3] = conversa.getMensagem();
            data.add(row);

            double resultado = insightsStrategyImpl.analiseDeSentimentosStrategy(data);

            assertEquals(0.875, resultado, 0.876);

        }

    }

    @Test
    public void testParaArquivosCsvComMensagensNegativas() {

        List<Conversas> negativo = new ArrayList<>();
        negativo.add(new Conversas("", "", "status1", "Terrible experience. I'm extremely disappointed."));
        negativo.add(new Conversas("", "", "status2", "Poor quality products. Waste of money"));

        List<String[]> data = new ArrayList<>();

        for (Conversas conversa : negativo) {
            String[] row = new String[4];
            row[0] = conversa.getId();
            row[1] = conversa.getData();
            row[2] = conversa.getStatus();
            row[3] = conversa.getMensagem();
            data.add(row);

            double resultado = insightsStrategyImpl.analiseDeSentimentosStrategy(data);

            assertEquals(0.25, resultado, 0.26);

        }


    }
}
