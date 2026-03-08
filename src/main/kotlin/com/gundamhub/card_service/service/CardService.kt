package com.gundamhub.card_service.service

import com.gundamhub.card_service.data.CardDto
import com.gundamhub.card_service.repositories.CardRepository
import org.springframework.stereotype.Service

@Service
class CardService(private val cardRepository: CardRepository) {

    fun findAll(): List<CardDto> = cardRepository.findAll().map { it.toDtoWithoutSet() }

    fun findById(id: String): CardDto? = cardRepository.findById(id).orElse(null)?.toDtoWithoutSet()

}

// mapping extension: avoid reading setInfo to prevent lazy initialization
private fun com.gundamhub.card_service.data.Card.toDtoWithoutSet() = CardDto(
    id = this.id,
    code = this.code,
    rarity = this.rarity,
    name = this.name,
    effect = this.effect,
    level = this.level,
    cost = this.cost,
    color = this.color,
    cardType = this.cardType,
    zone = this.zone,
    trait = this.trait,
    link = this.link,
    ap = this.ap,
    hp = this.hp,
    sourceTitle = this.sourceTitle,
    getIt = this.getIt,
    imageSmall = this.urlSmall,
    imageLarge = this.urlLarge
)
