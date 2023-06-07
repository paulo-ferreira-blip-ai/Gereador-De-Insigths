package com.takeblip.sumarizacao.service.impl;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import com.takeblip.sumarizacao.model.Conversas;
import com.takeblip.sumarizacao.service.LerArquivoCSV;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
public class LerArquivoCSVImpl implements LerArquivoCSV {
    @Override
    public List<Conversas> lerArquivoCSV(String caminhoArquivo) throws IOException {
        List<Conversas> objetos = new ArrayList<>();

        Reader reader = new FileReader(caminhoArquivo);
        CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
        String[] linha;
        while (true) {
            try {
                if (!((linha = csvReader.readNext()) != null)) break;
            } catch (CsvValidationException e) {
                throw new RuntimeException(e);
            }
            String data = linha[0];
            String status = linha[1];
            String mensagem = linha[2];

            Conversas conversas = new Conversas(data, status, mensagem);
            objetos.add(conversas);
        }

        return objetos;
    }


}

