package ch.gab.aitalksapi

import ch.gab.aitalksapi.im.client.IMClient
import ch.gab.aitalksapi.model.repository.ConversationRepository
import ch.gab.aitalksapi.model.repository.MessageRepository
import ch.gab.aitalksapi.service.ConversationService
import ch.gab.aitalksapi.service.MessageService
import ch.gab.aitalksapi.service.ModelService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class ConversationServiceTest {

    @Mock
    lateinit var conversationRepository: ConversationRepository

    @Mock
    lateinit var messageService: MessageService

    @Mock
    lateinit var imClient: IMClient

    @Mock
    lateinit var modelService: ModelService

    lateinit var conversationService: ConversationService

    @BeforeEach
    fun beforeEach() {
        conversationService = ConversationService(
            conversationRepository = conversationRepository,
            messageService = messageService,
            imClient = imClient,
            modelService = modelService
        )
    }
}
