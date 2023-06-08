package com.takeblip.sumarizacao.service.strategy;

import com.takeblip.sumarizacao.model.Conversas;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface LerArquivoCSV {
    List<Conversas> lerArquivoCSV(MultipartFile arquivo) throws IOException;
}
