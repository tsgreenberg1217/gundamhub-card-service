package com.gundamhub.card_service.repositories.jpa

import com.gundamhub.card_service.data.JpaCard
import org.springframework.context.annotation.Profile
import org.springframework.data.repository.CrudRepository

@Profile("local")
interface JpaCardRepository : CrudRepository<JpaCard, String>

