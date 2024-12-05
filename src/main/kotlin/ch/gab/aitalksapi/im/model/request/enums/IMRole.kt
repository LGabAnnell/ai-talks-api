package ch.gab.aitalksapi.im.model.request.enums

import com.fasterxml.jackson.annotation.JsonValue

enum class IMRole(private val value: String) {
    USER("user"),
    ASSISTANT("assistant"),
    SYSTEM("system");

    @JsonValue
    override fun toString(): String = value
}