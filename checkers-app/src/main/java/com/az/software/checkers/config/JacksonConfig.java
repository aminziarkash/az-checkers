package com.az.software.checkers.config;

import com.az.software.checkers.domain.model.Position;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper om = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Position.class, new PositionDeserializer());
        om.registerModule(module);
        return om;
    }
}