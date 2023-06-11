package com.takeblip.sumarizacao.service;

import com.takeblip.sumarizacao.exceptions.ConversaNotFoundException;
import com.takeblip.sumarizacao.model.Conversas;
import com.takeblip.sumarizacao.repository.ConversasRepository;
import com.takeblip.sumarizacao.service.impl.ConversasServiceImpl;
import com.takeblip.sumarizacao.service.strategy.InsightsStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.mongodb.assertions.Assertions.assertFalse;
import static org.bson.assertions.Assertions.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServiceImplTest {

    @InjectMocks
    private ConversasServiceImpl conversasServiceImpl;

    @Mock
    private ConversasService conversasService;

    @Mock
    private InsightsStrategy insightsStrategy;

    @Mock
    private ConversasRepository conversasRepository;

    private List<Conversas> listaDeConversas;


    @Test
    public void testDeveLancarExceptionSeNaoHouverDadosNoBanco() {

        assertThrows(ConversaNotFoundException.class, () -> {
            conversasServiceImpl.buscarTodos();
        });
    }

    @Test
    void testBuscarTodosDeveRetornarListaNaoVazia() {
        List<Conversas> conversas = new ArrayList<>();
        conversas.add(new Conversas("1", "2023-05-17", "status1", "mensagem1"));
        conversas.add(new Conversas("2", "2023-05-18", "status2", "mensagem2"));
        when(conversasRepository.findAll()).thenReturn(conversas);

        List<Conversas> resultado = conversasServiceImpl.buscarTodos();

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());

        verify(conversasRepository, times(1)).findAll();
    }

    @Test
    public void testDeveBuscarPorStatusComSucesso() {

        String statusParaBusca = "status1";

        List<Conversas> conversas = new ArrayList<>();
        conversas.add(new Conversas("1", "2023-05-17", "status1", "mensagem1"));
        conversas.add(new Conversas("2", "2023-05-18", "status2", "mensagem2"));
        conversas.add(new Conversas("3", "2023-05-19", "status1", "mensagem3"));

        when(conversasRepository.findByStatusIgnoreCase(statusParaBusca)).thenReturn(conversas.stream()
                .filter(c -> c.getStatus().equalsIgnoreCase(statusParaBusca))
                .collect(Collectors.toList()));

        List<Conversas> resultado = conversasServiceImpl.buscarPorStatus(statusParaBusca);
        //verificamos se a lista nao esta vazia
        assertFalse(resultado.isEmpty());

        //varificamos se a quantidade de status status1 na lista é igual ao resultado retornado
        assertEquals(2, resultado.size());

        //varificamos se todas as conversas no resultado tem o status1, como foram 2, verificamos se os 2 resultados
        //condizem com o que esta na lista.
        for (Conversas conversa : resultado) {
            assertEquals(statusParaBusca, conversa.getStatus());
        }
    }


    @Test
    public void testDeveSalvarDadosComSucesso() {
        List<Conversas> conversas = new ArrayList<>();
        conversas.add(new Conversas("1", "2023-05-17", "status1", "mensagem1"));
        conversas.add(new Conversas("2", "2023-05-18", "status2", "mensagem2"));

        Conversas conversaComID = new Conversas("3", "2023-05-19", "status3", "mensagem3");
        conversaComID.setId("12345");

        when(conversasRepository.save(any(Conversas.class))).thenReturn(conversaComID);

        List<Conversas> resultado = conversasServiceImpl.salvarDados(conversas);
        //verificamos se a lista nao esta vazia
        assertFalse(resultado.isEmpty());
        //verificamos se o tamanho da lista é igual ao resultado
        assertEquals(conversas.size(), resultado.size());
        //verificamos em todos os resultados se o Id nao esta nulo
        for (Conversas conversa : resultado) {
            assertNotNull(conversa.getId());
        }
    }


}
