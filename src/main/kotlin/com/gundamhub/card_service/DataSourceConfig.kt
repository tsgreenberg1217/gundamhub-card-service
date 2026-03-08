package com.gundamhub.card_service

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories


@Profile("local")
@Configuration
@EnableJpaRepositories(basePackages = ["com.gundamhub.card_service.repositories.jpa"])
class LocalDataSourceConfig


@Profile("cloud")
@Configuration
@EnableMongoRepositories(basePackages = ["com.gundamhub.card_service.repositories.mongo"])
class CloudDataSourceConfig {

    @Value("\${card-service.db-host}")
    private lateinit var dbHostName: String


    @Bean
    fun commandLineRunner(template: MongoTemplate) = CommandLineRunner {
        println("dbHostName from env = " + dbHostName);
        println("ACTIVE DB = " + template.getDb().getName());
        println("Collections = " + template.getCollectionNames());
        println("cards count = " + template.getCollection("cards").countDocuments());
    }

    @Bean
    fun getClient(): MongoClient {
        return MongoClients.create("mongodb://$dbHostName:27017");
    }

    @Bean
    fun mongoTemplate(mongoClient: MongoClient): MongoOperations {
        return MongoTemplate(mongoClient, "gundam-cards");
    }
}

