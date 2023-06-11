package com.takeblip.sumarizacao.controller.controllerfactory;

import com.takeblip.sumarizacao.controller.ConversasController;
import com.takeblip.sumarizacao.exceptions.ConversaNotFoundException;
import com.takeblip.sumarizacao.model.Conversas;
import com.takeblip.sumarizacao.model.dto.ConversasDtoResponse;
import com.takeblip.sumarizacao.repository.ConversasRepository;
import com.takeblip.sumarizacao.service.ConversasService;
import com.takeblip.sumarizacao.service.strategy.LerArquivoCSV;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ControllerTest {
    @InjectMocks
    private ConversasController conversasController;
    @Mock
    private ConversasService conversasService;

    @Mock
    private ConversasRepository conversasRepository;

    @Mock
    private LerArquivoCSV lerArquivoCSV;


    @Test
    public void testBuscarTodosComSucesso() throws ConversaNotFoundException {
        List<Conversas> conversasList = Arrays.asList(
                Conversas.builder().id("1").data("2023-05-17").status("Ativo").mensagem("Mensagem 1").build(),
                Conversas.builder().id("2").data("2023-05-18").status("Inativo").mensagem("Mensagem 2").build()
        );
        when(conversasService.buscarTodos()).thenReturn(conversasList);

        ResponseEntity<List<Conversas>> responseEntity = conversasController.buscarTodos();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(conversasList, responseEntity.getBody());
    }

    @Test
    public void testBuscarTodosConversaNotFoundException() throws ConversaNotFoundException {
        when(conversasService.buscarTodos()).thenThrow(ConversaNotFoundException.class);

        ResponseEntity<List<Conversas>> responseEntity = conversasController.buscarTodos();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }


    @Test
    public void testBuscarPorStatusExistenteRetornandoOk() {
        String status = "Pendente";

        ResponseEntity<List<Conversas>> reponse = conversasController.buscarPorStatus(status);
        assertEquals(HttpStatus.OK, reponse.getStatusCode());
        assertNotNull(reponse);

    }

    @Test
    public void testCarregarArquivoArquivoValidoRetornoOk() throws IOException {
        byte[] content = "conteúdo válido".getBytes();
        MockMultipartFile multipartFile = new MockMultipartFile("file", "arquivo.csv", "text/csv", content);
        ResponseEntity<Void> response = conversasController.carregarArquivo(multipartFile);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Realize outras verificações específicas para o cenário de arquivo válido
    }




    @Test
    public void testAtualizarConversaPorIdIdExistenteCamposValidosRetornoOk() {
        String id = "id_existente";
        Map<String, Object> camposAtualizados = new HashMap<>();

        ResponseEntity<Conversas> response = conversasController.atualizarConversaPorId(id, camposAtualizados);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeletarConversaPorIdIdExistenteRetornoNoContent() {
        String id = "id_existente";
        ResponseEntity<Void> response = conversasController.deletarConversaPorId(id);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testContagemDeOcorrenciasDadosExistentesRetornoOk() {

        ResponseEntity<List<ConversasDtoResponse>> response = conversasController.contagemDeOcorrencias();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    public void testContagemDeOcorrenciasDadosVaziosRetornoOk() {
        ResponseEntity<List<ConversasDtoResponse>> response = conversasController.contagemDeOcorrencias();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    public void testObterAnaliseSentimentosBemSucedidaRetornoOk() {
        ResponseEntity<Map<String, Object>> response = conversasController.obterAnaliseSentimentos();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


}
