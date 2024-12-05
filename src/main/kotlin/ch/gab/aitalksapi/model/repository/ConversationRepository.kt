package ch.gab.aitalksapi.model.repository

import ch.gab.aitalksapi.model.entity.Conversation
import org.springframework.data.jpa.repository.JpaRepository

interface ConversationRepository: JpaRepository<Conversation, Long>