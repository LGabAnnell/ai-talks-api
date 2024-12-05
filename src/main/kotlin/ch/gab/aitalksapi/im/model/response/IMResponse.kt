package ch.gab.aitalksapi.im.model.response

import ch.gab.aitalksapi.im.model.request.IMMessage
import ch.gab.aitalksapi.im.model.request.enums.IMModel
import com.fasterxml.jackson.annotation.JsonSetter

class IMResponse(
    val model: IMModel,
    val choices: List<IMChoice>,
)

class IMChoice(
    val message: IMMessage,
    @JsonSetter("finish_reason")
    val finishReason: String
)