package com.gundamhub.card_service.repositories.mongo

import com.gundamhub.card_service.data.Card
import com.gundamhub.card_service.data.CardFilter
import com.gundamhub.card_service.data.MongoCard
import com.gundamhub.card_service.data.toCard
import com.gundamhub.card_service.data.toMongoCard
import com.gundamhub.card_service.repositories.CardRepository
import org.springframework.context.annotation.Profile
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
@Profile("cloud")
class CardRepositoryMongoAdapter(
    private val delegate: MongoCardRepository,
    private val mongoOperations: MongoOperations,
) : CardRepository {
    override fun findAll(): Iterable<Card> = delegate.findAll().map { it.toCard() }
    override fun findById(id: String): Optional<Card> = delegate.findById(id).map { it.toCard() }
    override fun saveAll(cards: Iterable<Card>): Iterable<Card> {
        val mongoCards = cards.map { it.toMongoCard() }
        return delegate.saveAll(mongoCards).map { it.toCard() }
    }

    override fun findByFilter(filter: CardFilter): Iterable<Card> {
        val criteria = buildList {
            with(filter) {
                cost?.let { add(Criteria.where("cost").`is`(it)) }
                color?.let { add(Criteria.where("color").`is`(it)) }
                unit?.let { add(Criteria.where("cardType").`is`(it)) }
                level?.let { add(Criteria.where("level").`is`(it)) }
            }
        }

        val query = if (criteria.isEmpty()) Query() else Query(Criteria().andOperator(*criteria.toTypedArray()))
        return mongoOperations.find(query, MongoCard::class.java).map { it.toCard() }
    }

    override fun findByName(name: String): Iterable<Card> =
        delegate.findByNameContainingIgnoreCase(name).map { it.toCard() }

    override fun findByExactName(name: String): Iterable<Card> =
        delegate.findByName(name).map { it.toCard() }
}
