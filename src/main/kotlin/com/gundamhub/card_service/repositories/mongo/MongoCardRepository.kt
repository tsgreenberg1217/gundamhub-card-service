package com.gundamhub.card_service.repositories.mongo

import com.gundamhub.card_service.data.MongoCard
import org.springframework.context.annotation.Profile
import org.springframework.data.mongodb.repository.MongoRepository

@Profile("cloud")
interface MongoCardRepository : MongoRepository<MongoCard, String> {
    fun findByNameContainingIgnoreCase(name: String): List<MongoCard>
}

