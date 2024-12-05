package ch.gab.aitalksapi.model.repository

import ch.gab.aitalksapi.model.entity.Message
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface MessageRepository: JpaRepository<Message, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        select max(m.index) from Message m
        where m.conversationId = :conversationId
    """)
    fun findMaxIndexByConversationId(@Param("conversationId") conversationId: Long): Int?

    fun findByConversationIdOrderByIndexAsc(conversationId: Long): List<Message>
}