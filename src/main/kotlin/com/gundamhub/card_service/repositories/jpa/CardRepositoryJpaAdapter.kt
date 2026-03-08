package com.gundamhub.card_service.repositories.jpa

import com.gundamhub.card_service.data.Card
import com.gundamhub.card_service.data.JpaCard
import com.gundamhub.card_service.data.toCard
import com.gundamhub.card_service.data.toJpaCard
import com.gundamhub.card_service.repositories.CardRepository
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
@Profile("local")
class CardRepositoryJpaAdapter(
    private val delegate: JpaCardRepository
) : CardRepository {
    override fun findAll(): Iterable<Card> = delegate.findAll().map {
        it.toCard()
    }

    override fun findById(id: String): Optional<Card> = delegate.findById(id).map { it.toCard() }
    override fun saveAll(cards: Iterable<Card>): Iterable<Card> {
        val jpaCards = cards.map { it.toJpaCard() }
        return delegate.saveAll(jpaCards).map { it.toCard() }
    }
}
