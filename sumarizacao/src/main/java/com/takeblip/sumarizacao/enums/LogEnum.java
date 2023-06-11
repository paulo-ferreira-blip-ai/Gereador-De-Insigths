package com.takeblip.sumarizacao.enums;

public enum LogEnum {
    INFO_INICIO_ANALISE_DE_SENTIMENTOS_STRATEGY("Iniciando análiseDeSentimentosStrategy..."),
    INFO_FIM_ANALISE_DE_SENTIMENTOS_STRATEGY("AnáliseDeSentimentosStrategy concluída."),
    INFO_INICIO_BUSCAR_TODOS("Iniciando buscarTodos..."),
    INFO_FIM_BUSCAR_TODOS("BuscarTodos concluído."),
    INFO_INICIO_BUSCAR_POR_STATUS("Iniciando buscarPorStatus..."),
    INFO_FIM_BUSCAR_POR_STATUS("BuscarPorStatus concluído."),
    INFO_INICIO_SALVAR_DADOS("Iniciando salvarDados..."),
    INFO_FIM_SALVAR_DADOS("SalvarDados concluído."),
    INFO_INICIO_ALTERAR_CONVERSA_POR_ID("Iniciando alterarConversaPorId..."),
    INFO_FIM_ALTERAR_CONVERSA_POR_ID("AlterarConversaPorId concluído."),
    INFO_INICIO_DELETAR_CONVERSA_POR_ID("Iniciando deletarConversaPorId..."),
    INFO_FIM_DELETAR_CONVERSA_POR_ID("DeletarConversaPorId concluído."),
    INFO_INICIO_CONTAGEM_DE_OCORRENCIAS_SERVICE("Iniciando contagemDeOcorrenciasService..."),
    INFO_FIM_CONTAGEM_DE_OCORRENCIAS_SERVICE("ContagemDeOcorrenciasService concluída."),
    INFO_INICIO_ANALISE_DE_SENTIMENTOS_SERVICE("Iniciando análiseDeSentimentosService..."),
    INFO_FIM_ANALISE_DE_SENTIMENTOS_SERVICE("AnáliseDeSentimentosService concluída."),
    INFO_INICIO_LER_ARQUIVO_CSV("Iniciando leiturDeArquivoCSV..."),
    INFO_FIM_LER_ARQUIVO_CSV("leiturDeArquivoCSV finalizando..."),
    INFO_INICIO_CONTAGEM_DE_OCORRENCIAS_STRATEGY("Iniciando contagemDeOcorrenciasStrategy..."),
    INFO_FIM_CONTAGEM_DE_OCORRENCIAS_STRATEGY("ContagemDeOcorrenciasStrategy concluída.");

    private final String mensagem;

    LogEnum(String mensagem) {
        this.mensagem = mensagem;
    }

    @Override
    public String toString() {
        return mensagem;
    }

}
