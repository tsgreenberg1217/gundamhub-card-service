package com.gundamhub.card_service.data

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.gundamhub.card_service.repositories.CardRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component

@Component
@Profile("local")
class DataLoader(private val cardRepository: CardRepository) : CommandLineRunner {
    private val log = LoggerFactory.getLogger(DataLoader::class.java)

    override fun run(vararg args: String) {
        try {
            val resource = ClassPathResource("static/gd02.json")
            if (!resource.exists()) {
                log.warn("gd02.json not found on classpath; skipping load")
                return
            }

            val mapper = jacksonObjectMapper()
            val text = resource.inputStream.bufferedReader().use { it.readText() }

            // The file may contain JSON with comments at top — remove leading // comments if present
            val cleaned = text.lines().filterNot { it.trimStart().startsWith("//") }.joinToString("\n")

            val list: List<Map<String, Any?>> = mapper.readValue(cleaned)

            val cards = list.map { m ->
                val images = m["images"] as? LinkedHashMap<*, *>
                val set = m["set"] as? LinkedHashMap<*, *>
                Card(
                    id = m["id"].toString(),
                    code = m["code"]?.toString(),
                    rarity = m["rarity"]?.toString(),
                    name = m["name"]?.toString(),
                    effect = m["effect"]?.toString(),
                    level = m["level"]?.toString(),
                    cost = m["cost"]?.toString(),
                    color = m["color"]?.toString(),
                    cardType = m["cardType"]?.toString(),
                    zone = m["zone"]?.toString(),
                    trait = m["trait"]?.toString(),
                    link = m["link"]?.toString(),
                    ap = m["ap"]?.toString(),
                    hp = m["hp"]?.toString(),
                    sourceTitle = m["sourceTitle"]?.toString(),
                    setId = set?.get("id")?.toString(),
                    setName = set?.get("name")?.toString(),
                    urlSmall = images?.get("small")?.toString(),
                    urlLarge = images?.get("large")?.toString(),
                )
            }

            cardRepository.saveAll(cards)
            log.info("Loaded {} cards into database", cards.size)
        } catch (ex: Exception) {
            log.error("Failed to load gd02.json", ex)
        }
    }
}
