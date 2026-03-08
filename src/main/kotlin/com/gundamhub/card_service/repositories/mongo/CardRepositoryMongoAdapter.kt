package com.gundamhub.card_service.repositories.mongo

import com.gundamhub.card_service.data.Card
import com.gundamhub.card_service.data.toCard
import com.gundamhub.card_service.data.toMongoCard
import com.gundamhub.card_service.repositories.CardRepository
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository
import java.util.*

@Repository
@Profile("cloud")
class CardRepositoryMongoAdapter(
    private val delegate: MongoCardRepository
) : CardRepository {
    override fun findAll(): Iterable<Card> = delegate.findAll().map { it.toCard() }
    override fun findById(id: String): Optional<Card> = delegate.findById(id).map { it.toCard() }
    override fun saveAll(cards: Iterable<Card>): Iterable<Card> {
        val mongoCards = cards.map { it.toMongoCard() }
        return delegate.saveAll(mongoCards).map { it.toCard() }
    }
}
