package ch.gab.aitalksapi.im.client

import ch.gab.aitalksapi.im.model.request.IMAIRequest
import ch.gab.aitalksapi.im.model.request.IMMessage
import ch.gab.aitalksapi.im.model.request.enums.IMModel
import ch.gab.aitalksapi.im.model.request.enums.ProfileType
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class IMClient(@Qualifier("IMWebClient") val imWebClient: WebClient) {

    fun postMessage(stream: Boolean?, model: IMModel?, messages: List<IMMessage>): WebClient.ResponseSpec {
        return imWebClient.post()
            .body(
                Mono.just(
                    IMAIRequest(
                        messages = messages,
                        model = model,
                        profileType = ProfileType.CREATIVE,
                        stream = stream?: false,
                    )
                ),
                IMAIRequest::class.java
            )
            .retrieve()
    }
}