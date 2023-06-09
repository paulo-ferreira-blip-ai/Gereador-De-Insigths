package com.takeblip.sumarizacao.service.impl;

import com.takeblip.sumarizacao.configurations.ConversaNotFoundException;
import com.takeblip.sumarizacao.model.Conversas;
import com.takeblip.sumarizacao.model.dto.ConversasDtoResponse;
import com.takeblip.sumarizacao.repository.ConversasRepository;
import com.takeblip.sumarizacao.service.ConversasService;
import com.takeblip.sumarizacao.service.strategy.InsightsStrategy;
import com.takeblip.sumarizacao.service.strategy.LerArquivoCSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ConversasServiceImpl implements ConversasService {
    @Autowired
    private ConversasRepository conversasRepository;

    @Autowired
    private LerArquivoCSV lerArquivoCSV;

    @Autowired
    private InsightsStrategy insightsStrategy;

    //CRUD

    @Override
    public List<Conversas> buscarTodos() {
        return conversasRepository.findAll();
    }

    @Override
    public List<Conversas> findByStatus(String status) {
        return conversasRepository.findByStatusIgnoreCase(status);

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

    @Override
    public Conversas alterarConversaPorId(String id, Map<String, Object> camposAtualizados) {
        Optional<Conversas> conversaExistente = conversasRepository.findById(id);

        if (conversaExistente.isPresent()) {
            Conversas conversa = conversaExistente.get();

            for (Map.Entry<String, Object> entry : camposAtualizados.entrySet()) {
                String campo = entry.getKey();
                Object valor = entry.getValue();

                if (campo.equals("data") && valor instanceof String) {
                    conversa.setData((String) valor);
                } else if (campo.equals("status") && valor instanceof String) {
                    conversa.setStatus((String) valor);
                } else if (campo.equals("mensagem") && valor instanceof String) {
                    conversa.setMensagem((String) valor);
                }
                // Adicione outros casos para cada campo que pode ser atualizado
            }

            return conversasRepository.save(conversa);
        } else {
            throw new ConversaNotFoundException("Conversa não encontrada com o ID: " + id);
        }
    }

    @Override
    public void deletarConversaPorId(String id) {
        Optional<Conversas> conversaExistente = conversasRepository.findById(id);

        if (conversaExistente.isPresent()) {
            conversasRepository.deleteById(id);
        } else {
            throw new ConversaNotFoundException("Conversa não encontrada com o ID: " + id);
        }
    }

    //Insights

    @Override
    public List<ConversasDtoResponse> contagemDeOcorrencias() {
        List<Conversas> conversas = conversasRepository.findAll();


        Map<String, Integer> ocorrencias = insightsStrategy.contagemDeOcorrencias(conversas);

        List<Map.Entry<String, Integer>> ocorrenciasOrdenadas = new ArrayList<>(ocorrencias.entrySet());
        ocorrenciasOrdenadas.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        List<ConversasDtoResponse> respostaJson = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : ocorrenciasOrdenadas) {
            respostaJson.add(new ConversasDtoResponse(entry.getKey(), entry.getValue()));
        }
        return respostaJson;
    }


}
