package com.takeblip.sumarizacao;

import com.takeblip.sumarizacao.service.strategy.LerArquivoCSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TesteTecnicoApplication {
	@Autowired
	private LerArquivoCSV lerArquivoCSV;

	public static void main(String[] args) {
		SpringApplication.run(TesteTecnicoApplication.class, args);

	}
}
