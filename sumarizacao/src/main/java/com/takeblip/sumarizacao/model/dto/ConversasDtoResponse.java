package com.takeblip.sumarizacao.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversasDtoResponse {

    private String status;
    private int ocorrencias;

    @Override
    public String toString() {
        return "{" +
                "\"status\":\"" + status + '\"' +
                ", \"ocorrencias\":" + ocorrencias +
                '}';
    }

}
