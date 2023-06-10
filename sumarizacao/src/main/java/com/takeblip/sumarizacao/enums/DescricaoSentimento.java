package com.takeblip.sumarizacao.enums;

public enum DescricaoSentimento {
    MUITO_POSITIVO(1.0, "Muito Positivo"),
    POSITIVO(0.75, "Positivo"),
    NEUTRO(0.5, "Neutro"),
    NEGATIVO(0.25, "Negativo"),
    MUITO_NEGATIVO(0.0, "Muito Negativo");

    private double valor;
    private String descricao;

    DescricaoSentimento(double valor, String descricao) {
        this.valor = valor;
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public String getDescricao() {
        return descricao;
    }
}
