package com.gundamhub.card_service.data

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Lob

@Entity
class JpaCard(
    @Id
    val id: String = "",

    val code: String? = null,
    val rarity: String? = null,
    val name: String? = null,

    @Lob
    @Column(length = 20000)
    val effect: String? = null,

    val level: String? = null,
    val cost: String? = null,
    val color: String? = null,
    val cardType: String? = null,
    val zone: String? = null,
    val trait: String? = null,
    val link: String? = null,
    val ap: String? = null,
    val hp: String? = null,
    val sourceTitle: String? = null,
    val getIt: String? = null,
    val urlSmall: String? = null,
    val urlLarge: String? = null,
)

fun JpaCard.toCard() = Card(
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
    urlSmall = this.urlSmall,
    urlLarge = this.urlLarge
)

fun Card.toJpaCard() = JpaCard(
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
    urlSmall = this.urlSmall,
    urlLarge = this.urlLarge
)