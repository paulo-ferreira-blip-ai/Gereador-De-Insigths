package com.takeblip.sumarizacao.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("conversas")
public class Conversas implements Serializable {

    @Id
    private String id;

    private String data;

    private String status;

    private String mensagem;
}
