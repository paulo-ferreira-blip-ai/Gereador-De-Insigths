package com.takeblip.sumarizacao.service.strategy.impl;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
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

@Service
public class LerArquivoCSVImpl implements LerArquivoCSV {
    @Override
    public List<Conversas> lerArquivoCSV(MultipartFile arquivo) throws IOException {
        List<Conversas> objetos = new ArrayList<>();

        Reader reader = new InputStreamReader(arquivo.getInputStream(), StandardCharsets.UTF_8);
        CSVReader csvReader = new CSVReader(reader);
        String[] linha;
        while (true) {
            try {
                if (!((linha = csvReader.readNext()) != null)) break;
            } catch (CsvValidationException e) {
                throw new RuntimeException(e.getMessage());
            }
            String data = linha[0];
            String status = linha[1];
            String mensagem = linha[2];

            Conversas conversas = Conversas.builder()
                    .data(data)
                    .status(status)
                    .mensagem(mensagem)
                    .build();
            objetos.add(conversas);
        }

        return objetos;
    }


}

