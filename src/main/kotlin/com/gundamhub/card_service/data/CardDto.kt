package com.gundamhub.card_service.data

/**
 * DTO returned by the GraphQL API. Keep fields simple (Strings) matching the JSON/entity.
 */
data class CardDto(
    val id: String,
    val code: String?,
    val rarity: String?,
    val name: String?,
    val effect: String?,
    val level: String?,
    val cost: String?,
    val color: String?,
    val cardType: String?,
    val zone: String?,
    val trait: String?,
    val link: String?,
    val ap: String?,
    val hp: String?,
    val sourceTitle: String?,
    val getIt: String?,
//    val setId: String?,
    val imageSmall: String?,
    val imageLarge: String?
)

data class SetInfoDto(
    val id: String,
    val name: String,
)

data class ImageSetDto(
    val small: String?,
    val large: String?,
)

