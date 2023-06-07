package com.takeblip.sumarizacao.service;

import com.takeblip.sumarizacao.model.Conversas;

import java.io.IOException;
import java.util.List;

public interface LerArquivoCSV {
    List<Conversas> lerArquivoCSV(String caminhoArquivo) throws IOException;
}
