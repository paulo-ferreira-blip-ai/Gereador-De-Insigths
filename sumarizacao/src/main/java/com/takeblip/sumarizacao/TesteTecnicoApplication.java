package com.takeblip.sumarizacao;

import com.opencsv.CSVParser;
import com.opencsv.CSVReader;
import com.takeblip.sumarizacao.model.Conversas;
import com.takeblip.sumarizacao.service.LerArquivoCSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class TesteTecnicoApplication {
	@Autowired
	private LerArquivoCSV lerArquivoCSV;

	public static void main(String[] args) {
		SpringApplication.run(TesteTecnicoApplication.class, args);

	}
}
