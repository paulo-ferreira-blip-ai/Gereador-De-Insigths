package com.takeblip.sumarizacao.repository;

import com.takeblip.sumarizacao.model.Conversas;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConversasRepository extends MongoRepository<Conversas, Long> {
}
