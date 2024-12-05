package ch.gab.aitalksapi.model.repository

import ch.gab.aitalksapi.model.entity.Model
import org.springframework.data.jpa.repository.JpaRepository

interface ModelRepository: JpaRepository<Model, Long> {
    fun findByName(model: String): Model
}