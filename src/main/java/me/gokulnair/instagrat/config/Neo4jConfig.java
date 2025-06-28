package me.gokulnair.instagrat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.core.transaction.ReactiveNeo4jTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;

@Configuration
public class Neo4jConfig {

    @Bean
    public ReactiveTransactionManager reactiveTransactionManager(org.neo4j.driver.Driver driver) {
        return new ReactiveNeo4jTransactionManager(driver);
    }
}