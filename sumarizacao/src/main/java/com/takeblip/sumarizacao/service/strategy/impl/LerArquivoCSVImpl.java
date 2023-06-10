package com.takeblip.sumarizacao.service.strategy.impl;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.takeblip.sumarizacao.enums.LogEnum;
import com.takeblip.sumarizacao.model.Conversas;
import com.takeblip.sumarizacao.service.strategy.LerArquivoCSV;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class LerArquivoCSVImpl implements LerArquivoCSV {
    private static final Logger LOGGER = Logger.getLogger(LerArquivoCSVImpl.class.getName());

    @Override
    public List<Conversas> lerArquivoCSV(MultipartFile arquivo) throws IOException {
        //O método recebe como parametro dados do tipo MultipartFile que é uma interface do Spring
        //que representa arquivos enviados por meio de solicitações HTTP form-data, permitindo o envio de arquivos
        //No nosso caso estamos lendo arquivos CSV

        LOGGER.log(Level.INFO, LogEnum.INFO_INICIO_LER_ARQUIVO_CSV.toString());

        //Criamos um mapa vazio para armazenar os dados no final do processo
        List<Conversas> objetos = new ArrayList<>();
        //Aqui estamos usando Classe abstrata para leitura de fluxos de caracteres Reader
        //que recebe do método InputStreamReader uma conversão de dados bytes para um fluxo de caracteres permitindo
        //o Reader ler os dados
        //O uso do UTF-8 como codificador de caracteres garante que os caracteres do arquivo sejam interpretados
        //corretamente, independentemente do idioma ou dos caracteres especiais que possam estar presentes.
        Reader reader = new InputStreamReader(arquivo.getInputStream(), StandardCharsets.UTF_8);
        //Aqui está sendo criada uma instância de CSVReader que recebe o Reader criado anteriormente.
        //CSVReader é uma classe fornecida pela biblioteca OpenCSV que facilita a leitura de arquivos CSV.
        //Essa classe permite ler o conteúdo do arquivo CSV de forma eficiente e realizar o parsing das linhas e colunas.
        CSVReader csvReader = new CSVReader(reader);

        //Aqui estamos usando um loop para ler cada linha do arquivo CSV
        //A varivel String abaixo será usada para armazenar os valores de cada linha do arquivo CSV
        String[] linha;
        //O While é usado para percorrer todas as linhas do arquivo CSV ate que não haja mais linhas a serem lidas
        while (true) {
            //
            try {
                //O método readNext() do objeto csvReader é chamado para ler a próxima linha do arquivo CSV e armazená-la
                // na variável linha. Esse método retorna um array de strings contendo os valores de cada coluna da linha.
                //também verificamos se a linha lida é diferente de null, se for null, significa que não há mais linhas
                // então encerramos o loop com o break
                if (!((linha = csvReader.readNext()) != null)) break;
            } catch (CsvValidationException e) {
                throw new RuntimeException(e.getMessage());
            }
            //Aqui estamos atribuindo as linhas 0(Data) 1(Status) 2(Mensagem)
            String data = linha[0];
            String status = linha[1];
            String mensagem = linha[2];

            //Aqui estamos atribuindo os valores para criar um objeto na entidade Conversas
            //com isso poderemos manipular os dados do arquivo, salvando no banco e posteriormente
            //criando insights.
            Conversas conversas = Conversas.builder()
                    .data(data)
                    .status(status)
                    .mensagem(mensagem)
                    .build();
            //Aqui estamos atribuindo o objeto a variavel criada no inicio para ser retornado onde o método é chamado
            objetos.add(conversas);
        }
        LOGGER.log(Level.INFO, LogEnum.INFO_FIM_LER_ARQUIVO_CSV.toString() + " Resultado: {0}", objetos.size());


        return objetos;
    }


}

