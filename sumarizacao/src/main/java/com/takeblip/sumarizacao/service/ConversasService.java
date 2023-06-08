package com.takeblip.sumarizacao.service;

import com.takeblip.sumarizacao.model.Conversas;

import java.util.List;

public interface ConversasService {
    public List<Conversas> buscarTodos();

    public Conversas buscarPorCodigo(String codigo);

    public List<Conversas> salvarDados(List<Conversas> conversas);

}
