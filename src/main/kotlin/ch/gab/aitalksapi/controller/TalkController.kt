package ch.gab.aitalksapi.controller

import ch.gab.aitalksapi.controller.request.InitialRequest
import ch.gab.aitalksapi.controller.request.MessageRequest
import ch.gab.aitalksapi.service.ConversationService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class TalkController(
    val conversationService: ConversationService,
) {
    @PostMapping("/initiate")
    fun initiate(
        @RequestBody @Validated initialMessage: InitialRequest,
    ) = conversationService.initiate(
        userModelName = initialMessage.userModel,
        userModelNickName = initialMessage.userModelNickname,
        assistantModelName = initialMessage.assistantModel,
        assistantModelNickname = initialMessage.assistantModelNickname,
        message = initialMessage.message,
        systemInstructions = initialMessage.systemInstructions,
        userInstructions = initialMessage.userInstructions,
        stream = initialMessage.stream,
    )

    @PostMapping("/accept")
    fun accept(
        @RequestParam("conversationId") conversationId: Long,
        @RequestBody message: MessageRequest,
    ) = conversationService.acceptResponse(conversationId, message.message)

    @GetMapping("/next")
    fun next(
        @RequestParam conversationId: Long,
    ) = conversationService.next(conversationId)
}
