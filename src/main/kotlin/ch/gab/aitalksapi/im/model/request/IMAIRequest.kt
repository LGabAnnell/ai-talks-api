package ch.gab.aitalksapi.im.model.request

import ch.gab.aitalksapi.im.model.request.enums.IMModel
import ch.gab.aitalksapi.im.model.request.enums.ProfileType
import com.fasterxml.jackson.annotation.JsonGetter

data class IMAIRequest(
    val messages: List<IMMessage>,
    val model: IMModel? = IMModel.LLAMA3,
    @get:JsonGetter("profile_type") val profileType: ProfileType? = ProfileType.STANDARD,
    @get:JsonGetter("max_tokens") val maxTokens: Int? = 5000,
    val stream: Boolean? = false,
)
