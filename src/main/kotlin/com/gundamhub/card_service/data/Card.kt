package com.gundamhub.card_service.data

data class Card(
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
    val urlSmall: String? = null,
    val urlLarge: String? = null,
)