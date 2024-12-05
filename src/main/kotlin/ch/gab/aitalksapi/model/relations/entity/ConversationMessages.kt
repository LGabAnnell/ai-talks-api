package ch.gab.aitalksapi.model.relations.entity

import jakarta.persistence.*

@Entity
data class ConversationMessages(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    @Column(name = "conversation_id")
    val conversationId: Long?,
    @Column(name = "message_id")
    val messageId: Long?
) {
    constructor(): this(null, null, null)
}