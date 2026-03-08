package com.gundamhub.card_service.data

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "cards")
class MongoCard(
    @Id
    val id: String = "",

    val code: String? = null,
    val rarity: String? = null,
    val name: String? = null,

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
    val images: MongoImageSet? = null,
)

data class MongoImageSet(
    val small: String? = null,
    val large: String? = null
)

fun MongoCard.toCard() = Card(
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
    urlSmall = this.images?.small,
    urlLarge = this.images?.large,
)

fun Card.toMongoCard() = MongoCard(
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

)