package ch.gab.aitalksapi.service

import ch.gab.aitalksapi.controller.response.ContinuationResponse
import ch.gab.aitalksapi.controller.response.InitiatingResponse
import ch.gab.aitalksapi.im.client.IMClient
import ch.gab.aitalksapi.im.model.request.IMMessage
import ch.gab.aitalksapi.im.model.request.enums.IMModel
import ch.gab.aitalksapi.im.model.response.IMResponse
import ch.gab.aitalksapi.model.entity.Conversation
import ch.gab.aitalksapi.model.entity.Message
import ch.gab.aitalksapi.model.entity.Model
import ch.gab.aitalksapi.model.repository.ConversationRepository
import ch.gab.aitalksapi.statemachine.modelFromState
import ch.gab.aitalksapi.statemachine.nextState
import ch.gab.aitalksapi.statemachine.roleFromState
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.stream.IntStream


@Component
class ConversationService(
    private val conversationRepository: ConversationRepository,
    private val modelService: ModelService,
    private val imClient: IMClient,
    private val messageService: MessageService
) {
    @Transactional
    fun initiate(message: String,
                 userModelName: String,
                 assistantModelName: String,
                 systemInstructions: String,
                 userInstructions: String,
                 stream: Boolean?,
                 userModelNickName: String = "",
                 assistantModelNickname: String = "",
    ): InitiatingResponse {

        val initialResponse = imClient.postMessage(
            model = IMModel.fromString(userModelName),
            messages = listOf(
                IMMessage(
                    role = "system",
                    content = systemInstructions
                ),
                IMMessage(
                    role = "user",
                    content = message
                )
            ),
            stream = stream,
        )
            .bodyToMono(IMResponse::class.java)
            .doOnError {
                LoggerFactory.getLogger(ConversationService::class.java)
                    .error("Error while initiating conversation", it)
                throw IllegalStateException("Error while initiating conversation", it)
            }
            .block()

        val conversation = Conversation(
            userModel = modelService.save(Model(name = userModelName, nickName = userModelNickName)),
            assistantModel = modelService.save(Model(name = assistantModelName, nickName = assistantModelNickname)),
            systemInstructions = systemInstructions,
            userInstructions = userInstructions,
        )

        conversationRepository.save(conversation)

        acceptResponse(
            message = initialResponse!!.choices[0].message.content,
            conversationId = conversation.id,
        )

        return InitiatingResponse(
            message = initialResponse.choices[0].message.content,
            conversationId = conversation.id,
            model = initialResponse.model.name,
        )
    }

    @Transactional
    fun acceptResponse(conversationId: Long, message: String) {
        val conversation = conversationRepository.findById(conversationId).orElseThrow {
            IllegalArgumentException("Conversation with id $conversationId does not exist")
        }

        val role: String = roleFromState(conversation.state)

        val nextState = nextState(conversation.state)

        conversation.state = nextState

        val savedMessage = messageService.saveMessageNoTransactional(message, role, conversationId)

        conversation.messages += savedMessage

        conversationRepository.save(conversation)
    }

    @Transactional
    fun next(conversationId: Long): ContinuationResponse {
        val conversation = conversationRepository.findById(conversationId).orElseThrow {
            IllegalArgumentException("Conversation with id $conversationId does not exist")
        }

        val role = roleFromState(conversation.state)

        val model = modelFromState(conversation.state, conversation.userModel, conversation.assistantModel)

        var messages: List<Message> = messageService.messages(conversationId)

        val messageLengthIsPair = messages.size % 2 == 0

        if (messageLengthIsPair) {
            messages[0].role = "assistant"
        } else {
            messages[0].role = "user"
        }

        messages = IntStream.range(0, messages.size)
            .mapToObj { index ->
                if (index == 0) {
                    return@mapToObj messages[index]
                }

                if (messages[index - 1].role == "user") {
                    messages[index].role = "assistant"
                }

                if (messages[index - 1].role == "assistant") {
                    messages[index].role = "user"
                }

                messages[index]
            }.toList()

        if (role == "user") {
            messages = listOf(Message(
                role = "system",
                content = conversation.systemInstructions
            )) + messages
        }

        if (role == "assistant" && !conversation.userInstructions.isNullOrEmpty()) {
            messages = listOf(Message(
                role = "system",
                content = conversation.userInstructions
            )) + messages
        }

        val imResponse = imClient.postMessage(
            model = IMModel.fromString(model!!.name),
            messages = messages.map { IMMessage(
                content = it.content!!,
                role = it.role!!
            ) },
            stream = false,
        ).bodyToMono(IMResponse::class.java).block()

        acceptResponse(
            message = imResponse!!.choices[0].message.content,
            conversationId = conversationId,
        )

        return ContinuationResponse(
            message = imResponse.choices[0].message.content,
            model = imResponse.model.name
        )
    }
}