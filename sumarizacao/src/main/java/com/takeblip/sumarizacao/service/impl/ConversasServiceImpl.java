package com.takeblip.sumarizacao.service.impl;

import com.takeblip.sumarizacao.enums.DescricaoSentimento;
import com.takeblip.sumarizacao.enums.LogEnum;
import com.takeblip.sumarizacao.exceptions.ConversaNotFoundException;
import com.takeblip.sumarizacao.exceptions.InsightsException;
import com.takeblip.sumarizacao.model.Conversas;
import com.takeblip.sumarizacao.model.dto.ConversasDtoResponse;
import com.takeblip.sumarizacao.repository.ConversasRepository;
import com.takeblip.sumarizacao.service.ConversasService;
import com.takeblip.sumarizacao.service.strategy.InsightsStrategy;
import com.takeblip.sumarizacao.service.strategy.LerArquivoCSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


@Service
public class ConversasServiceImpl implements ConversasService {
    private static final Logger LOGGER = Logger.getLogger(ConversasServiceImpl.class.getName());

    @Autowired
    private ConversasRepository conversasRepository;

    @Autowired
    private LerArquivoCSV lerArquivoCSV;

    @Autowired
    private InsightsStrategy insightsStrategy;

    //CRUD

    @Override
    public List<Conversas> buscarTodos() {
        LOGGER.log(Level.INFO, LogEnum.INFO_INICIO_BUSCAR_TODOS.toString());

        var result = conversasRepository.findAll();

        if (result.isEmpty()){
            throw new ConversaNotFoundException("Conversas não encontradas");
        }
        LOGGER.log(Level.INFO, LogEnum.INFO_FIM_BUSCAR_TODOS.toString() + " Resultados: {0}", result);

        return result;
    }

    @Override
    public List<Conversas> buscarPorStatus(String status) {
        LOGGER.log(Level.INFO, LogEnum.INFO_INICIO_BUSCAR_POR_STATUS.toString());

        var result = conversasRepository.findByStatusIgnoreCase(status);

        LOGGER.log(Level.INFO, LogEnum.INFO_FIM_BUSCAR_POR_STATUS.toString() + " Resultados: {0}", result);

        return result;
    }

    @Override
    public List<Conversas> salvarDados(List<Conversas> conversas) {
        LOGGER.log(Level.INFO, LogEnum.INFO_INICIO_SALVAR_DADOS.toString());

        //Excluir todos os dados existentes antes de salvar os novos
        //para facilitar a troca de arquivos e nao precisar dropar a collection sempre que quiser usar
        //um novo arquivo csv
        conversasRepository.deleteAll();

        List<Conversas> savedConversas = new ArrayList<>();
        for (Conversas conversa : conversas) {
            savedConversas.add(conversasRepository.save(conversa));
        }

        LOGGER.log(Level.INFO, LogEnum.INFO_FIM_SALVAR_DADOS.toString() + " Resultados: {0}", savedConversas);

        return savedConversas;
    }

    @Override
    public Conversas alterarConversaPorId(String id, Map<String, Object> camposAtualizados) {
        //Esse método proporciona flexibilidade para atualizar apenas os campos desejados da conversa sem a necessidade
        // de escrever código específico para cada campo individualmente.

        LOGGER.log(Level.INFO, LogEnum.INFO_INICIO_ALTERAR_CONVERSA_POR_ID.toString());
        //Aqui estamos fazendo uma busca no banco pelo Id fornecido, caso não seja encontrado lançamos uma exception
        Conversas conversa = conversasRepository.findById(id)
                .orElseThrow(() -> new ConversaNotFoundException("Conversa não encontrada com o ID: " + id));

        //Aqui estamos percorrendo o objeto Map que o usuario enviou com as alterações desejadas
        //A expressão lambda (campo, valor) -> definirValorDoCampo(conversa, campo, valor) é utilizada para cada par
        //de chave-valor no mapa. Para cada iteração do loop, o método definirValorDoCampo é chamado, passando como
        //argumentos a instância de conversa, o nome do campo e o valor correspondente.
        //Essa abordagem permite percorrer todos os campos atualizados no mapa e chamar o método definirValorDoCampo
        //para cada um deles.
        camposAtualizados.forEach((campo, valor) -> definirValorDoCampo(conversa, campo, valor));

        LOGGER.log(Level.INFO, LogEnum.INFO_FIM_ALTERAR_CONVERSA_POR_ID.toString() + " Resultado: {0}", conversa);

        return conversasRepository.save(conversa);
    }

    private void definirValorDoCampo(Conversas conversa, String campo, Object valor) {
        //Aqui o nome do campo é utilizado para obter o campo declarado na classe Conversas e, em seguida, o valor é
        //definido nesse campo na instância de conversa.
        try {
            Field field = Conversas.class.getDeclaredField(campo);
            field.setAccessible(true);
            field.set(conversa, valor);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // Lidar com campos inválidos ou inacessíveis, se necessário
        }
    }


    @Override
    public void deletarConversaPorId(String id) {
        LOGGER.log(Level.INFO, LogEnum.INFO_INICIO_DELETAR_CONVERSA_POR_ID.toString());

        Optional<Conversas> conversaExistente = conversasRepository.findById(id);

        if (conversaExistente.isPresent()) {
            conversasRepository.deleteById(id);
        } else {
            throw new ConversaNotFoundException("Conversa não encontrada com o ID: " + id);
        }

        LOGGER.log(Level.INFO, LogEnum.INFO_FIM_DELETAR_CONVERSA_POR_ID.toString() + " Resultado: {0}", id);
    }

    // Insights

    @Override
    public List<ConversasDtoResponse> contagemDeOcorrenciasService() {
        LOGGER.log(Level.INFO, LogEnum.INFO_INICIO_CONTAGEM_DE_OCORRENCIAS_SERVICE.toString());

        List<Conversas> conversas = conversasRepository.findAll();

        Map<String, Integer> ocorrencias = insightsStrategy.contagemDeOcorrencias(conversas);

        //tranferindo os dados do metodo na Strategy para uma lista de chave e valor, usamos a classe Map.Entry
        //para fazer aa atribuição onde a chave é o status String e o valor a quantidade de ocorrencias Integer
        //por fim utilizamos o metodo sort, comparingByValue e reverseOrder para ordenar os dados de forma decrescente
        //após isso os dados estruturados são enviados para a linha seguinte para montar a resposta Json
        List<Map.Entry<String, Integer>> ocorrenciasOrdenadas = new ArrayList<>(ocorrencias.entrySet());
        ocorrenciasOrdenadas.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        //montando resposta Json
        List<ConversasDtoResponse> respostaJson = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : ocorrenciasOrdenadas) {
            respostaJson.add(new ConversasDtoResponse(entry.getKey(), entry.getValue()));
        }

        LOGGER.log(Level.INFO, LogEnum.INFO_FIM_CONTAGEM_DE_OCORRENCIAS_SERVICE.toString() + " Resultados: {0}", respostaJson);

        return respostaJson;
    }

    @Override
    public Map<String, Object> analiseDeSentimentosService() {
        LOGGER.log(Level.INFO, LogEnum.INFO_INICIO_ANALISE_DE_SENTIMENTOS_SERVICE.toString());

        try {
            //Aqui fazemos um busca no banco por todas as conversas armazenando em uma lista
            List<Conversas> conversas = conversasRepository.findAll();

            //Aqui estamos convertendo a lista de Objetos do tipo da nossa entidade para um array de Strings
            //adaptando-se ao formato que o método analiseDeSentimentoStrategy esta esperando
            List<String[]> data = getStrings(conversas);

            //Aqui estamos enviando os dados retonados da conversão para serem usados no método de analise de sentimentos
            //na classe de implementação analiseDeSentimentoStrategy
            double mediaDeSentimento = insightsStrategy.analiseDeSentimentosStrategy(data);

            //Aqui enviamos os dados retornados para o método que fará a atribuição do valor retornado para seu devido
            //grau de sentimento descritos e configurados na classe Enum
            String descricaoSentimento = getDescricaoSentimento(mediaDeSentimento);

            //Aqui montamos uma resposta Json para o usuario
            Map<String, Object> saida = new HashMap<>();
            saida.put("mediaDeSentimento", mediaDeSentimento);
            saida.put("descricaoSentimento", descricaoSentimento);

            LOGGER.log(Level.INFO, LogEnum.INFO_FIM_ANALISE_DE_SENTIMENTOS_SERVICE.toString() + " Resultados: {0}", saida);

            return saida;

        } catch (Exception ex) {
            throw new InsightsException("Erro na análise de sentimentos", ex.getCause());
        }
    }


    private static List<String[]> getStrings(List<Conversas> conversas) {

        //cria um novo array de strings com tamanho 4 e preenche cada elemento do array com os valores extraídos do
        //objeto Conversas. Os valores são atribuídos às posições corretas do array, onde o índice 0
        //contém o ID, o índice 1 contém a data, o índice 2 contém o status e o índice 3 contém a mensagem.
        List<String[]> data = new ArrayList<>();

        for (Conversas conversa : conversas) {
            String[] row = new String[4];
            row[0] = conversa.getId();
            row[1] = conversa.getData();
            row[2] = conversa.getStatus();
            row[3] = conversa.getMensagem();

            //Após o preenchimento do array adicionamos à lista "data", atraves do contador "for" esse processo é feito
            //para cada objeto na lista "conversa".
            data.add(row);
        }

        return data;
    }

    private String getDescricaoSentimento(double valorDoSentimento) {

        //Aqui estamos fazendo uma busca à classe Enum DescricaoSentimento para atribuir o valor retornado do
        //método analiseDeSentimentoStrategy ao seu devido "sentimento" configurado na classe enum
        //Lá temos um parametro de avaliação onde:
        //entre 0 e 0.25 é muito negativo
        //entre 0.25 e 0.5 é negativo
        //entre 0.5 e 0.75 é neutro
        //e entre 0.75 e 1.0 é muito positivo
        //Esses valores são basados na estrutura da biblioteca StanfordCoreNLP
        for (DescricaoSentimento sentimento : DescricaoSentimento.values()) {
            if (valorDoSentimento >= sentimento.getValor()) {
                return sentimento.getDescricao();
            }
        }

        return "Desconhecido";
    }


}
