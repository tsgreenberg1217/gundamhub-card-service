package com.gundamhub.card_service.repositories.jpa

import com.gundamhub.card_service.data.Card
import com.gundamhub.card_service.data.CardFilter
import com.gundamhub.card_service.data.JpaCard
import com.gundamhub.card_service.data.toCard
import com.gundamhub.card_service.data.toJpaCard
import com.gundamhub.card_service.repositories.CardRepository
import org.springframework.context.annotation.Profile
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
@Profile("local")
class CardRepositoryJpaAdapter(
    private val delegate: JpaCardRepository
) : CardRepository {
    override fun findAll(): Iterable<Card> = delegate.findAll().map { it.toCard() }

    override fun findById(id: String): Optional<Card> = delegate.findById(id).map { it.toCard() }

    override fun saveAll(cards: Iterable<Card>): Iterable<Card> {
        val jpaCards = cards.map { it.toJpaCard() }
        return delegate.saveAll(jpaCards).map { it.toCard() }
    }

    override fun findByFilter(filter: CardFilter): Iterable<Card> {
        val spec = Specification<JpaCard> { root, _, cb ->
            val predicates = listOfNotNull(
                filter.name?.let { cb.like(cb.lower(root.get("name")), "%${it.lowercase()}%") },
                filter.code?.let { cb.equal(root.get<String>("code"), it) },
                filter.rarity?.let { cb.equal(root.get<String>("rarity"), it) },
                filter.level?.let { cb.equal(root.get<String>("level"), it) },
                filter.cost?.let { cb.equal(root.get<String>("cost"), it) },
                filter.color?.let { cb.equal(root.get<String>("color"), it) },
                filter.cardType?.let { cb.equal(root.get<String>("cardType"), it) },
                filter.zone?.let { cb.equal(root.get<String>("zone"), it) },
                filter.trait?.let { cb.equal(root.get<String>("trait"), it) },
                filter.link?.let { cb.equal(root.get<String>("link"), it) },
                filter.ap?.let { cb.equal(root.get<String>("ap"), it) },
                filter.hp?.let { cb.equal(root.get<String>("hp"), it) },
                filter.sourceTitle?.let { cb.equal(root.get<String>("sourceTitle"), it) },
                filter.setId?.let { cb.equal(root.get<String>("setId"), it) },
                filter.setName?.let { cb.equal(root.get<String>("setName"), it) },
            )
            cb.and(*predicates.toTypedArray())
        }
        return delegate.findAll(spec).map { it.toCard() }
    }
}
