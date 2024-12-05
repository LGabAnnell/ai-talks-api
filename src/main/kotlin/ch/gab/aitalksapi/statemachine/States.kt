package ch.gab.aitalksapi.statemachine

import ch.gab.aitalksapi.statemachine.model.enums.State

val illegalState =
    IllegalStateException("You done goofed up, state is null")

fun nextState(currentState: State?): State = when (currentState) {
    State.INITIATING -> State.USER_TO_ASSISTANT
    State.USER_TO_ASSISTANT -> State.ASSISTANT_TO_USER
    State.ASSISTANT_TO_USER -> State.USER_TO_ASSISTANT
    null -> throw illegalState
}

fun roleFromState(currentState: State?) = when (currentState) {
    State.INITIATING -> "user"
    State.USER_TO_ASSISTANT -> "assistant"
    State.ASSISTANT_TO_USER -> "user"
    null -> throw illegalState
}

fun inverseRoleFromState(currentState: State?) = when (currentState) {
    State.INITIATING -> "assistant"
    State.USER_TO_ASSISTANT -> "user"
    State.ASSISTANT_TO_USER -> "assistant"
    null -> throw illegalState
}

fun <T> modelFromState(currentState: State?, userModel: T, assistantModel: T): T = when (currentState) {
    State.INITIATING -> userModel
    State.USER_TO_ASSISTANT -> assistantModel
    State.ASSISTANT_TO_USER -> userModel
    null -> throw illegalState
}