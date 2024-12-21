package ch.gab.aitalksapi.controller.request

import jakarta.validation.constraints.NotNull

class InitialRequest(
    @NotNull val userModel: String,
    val userNickName: String = "",
    @NotNull val assistantModel: String,
    val assistantNickName: String = "",
    @NotNull val message: String,
    @NotNull val systemInstructions: String,
    @NotNull val userInstructions: String,
    val stream: Boolean?
)