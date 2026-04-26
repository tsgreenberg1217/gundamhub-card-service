package com.gundamhub.card_service.graphql

import com.gundamhub.card_service.data.CardDto
import com.gundamhub.card_service.data.CardFilter
import com.gundamhub.card_service.service.CardService
import org.slf4j.LoggerFactory
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class CardController(
    private val cardService: CardService
) {
    private val log = LoggerFactory.getLogger(CardController::class.java)

    @QueryMapping
    fun cards(@Argument filter: CardFilter?): List<CardDto> = try {
        if (filter != null) cardService.findByFilter(filter) else cardService.findAll()
    } catch (ex: Exception) {
        log.error("Error fetching cards", ex)
        emptyList()
    }

    @QueryMapping
    fun cardById(@Argument id: String): CardDto? {
        return try {
            cardService.findById(id)
        } catch (ex: Exception) {
            log.error("Error fetching card $id", ex)
            null
        }
    }

}
