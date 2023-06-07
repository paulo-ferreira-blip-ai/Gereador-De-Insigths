package com.takeblip.sumarizacao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class ConversasServiceImpl {
    @Autowired
    private MongoTemplate mongoTemplate;


}
