package ch.gab.aitalksapi.controller.response

data class InitiatingResponse(
    val message: String? = "",
    val conversationId: Long? = null,
    val model: String? = ""
)