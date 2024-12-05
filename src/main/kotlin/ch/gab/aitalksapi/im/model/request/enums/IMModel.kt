package ch.gab.aitalksapi.im.model.request.enums

import com.fasterxml.jackson.annotation.JsonValue

enum class IMModel(private val value: String) {
    LLAMA3("llama3"),
    MIXTRAL("mixtral");

    companion object {
        fun fromString(value: String): IMModel = requireNotNull(IMModel.entries.find { it.value == value.lowercase() }) {
            "Unknown IMModel: $value"
        }
    }

    @JsonValue
    override fun toString(): String = value
}