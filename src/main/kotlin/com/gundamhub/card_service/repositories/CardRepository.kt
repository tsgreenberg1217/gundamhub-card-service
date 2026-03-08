package com.gundamhub.card_service.repositories

import com.gundamhub.card_service.data.Card
import java.util.Optional

interface CardRepository {
    fun findAll(): Iterable<Card>
    fun findById(id: String): Optional<Card>
    fun saveAll(cards: Iterable<Card>): Iterable<Card>
}

