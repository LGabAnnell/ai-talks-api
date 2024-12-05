package ch.gab.aitalksapi.controller.request

import jakarta.validation.constraints.NotNull

data class MessageRequest(
    @NotNull
    val message: String,
    @NotNull
    val role: String
)