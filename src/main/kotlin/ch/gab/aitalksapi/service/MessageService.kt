package ch.gab.aitalksapi.service

import ch.gab.aitalksapi.model.entity.Message
import ch.gab.aitalksapi.model.repository.MessageRepository
import org.springframework.stereotype.Component

@Component
class MessageService(
    private val messageRepository: MessageRepository
) {
    fun saveMessageNoTransactional(message: String, role: String, conversationId: Long): Message =
        messageRepository.findMaxIndexByConversationId(conversationId)
            .let { index ->
                messageRepository.save(
                    Message(
                        role = role,
                        content = message,
                        index = (index ?: 0) + 1,
                        conversationId = conversationId
                    )
                )
            }

    fun messages(conversationId: Long) = messageRepository.findByConversationIdOrderByIndexAsc(conversationId)
}