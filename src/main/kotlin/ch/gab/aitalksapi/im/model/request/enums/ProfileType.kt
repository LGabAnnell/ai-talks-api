package ch.gab.aitalksapi.im.model.request.enums

import com.fasterxml.jackson.annotation.JsonValue

enum class ProfileType(private val value: String) {
    CREATIVE("creative"),
    STANDARD("standard"),
    STRICT("strict");

    @JsonValue
    override fun toString(): String = value
}