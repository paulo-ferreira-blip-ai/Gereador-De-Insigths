package com.takeblip.sumarizacao.repository;

import com.takeblip.sumarizacao.model.Conversas;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversasRepository extends MongoRepository<Conversas, String> {
    List<Conversas> findByStatusIgnoreCase(String status);
}
