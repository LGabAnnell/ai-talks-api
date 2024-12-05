package ch.gab.aitalksapi.im.configuration.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.ClientHttpRequest
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class IMWebClientConfiguration {

    @Value("\${im.api.token}")
    lateinit var imApiToken: String

    @Value("\${im.api.product_id}")
    lateinit var productId: String

    @Bean("IMWebClient")
    fun webClient(builder: WebClient.Builder): WebClient {
        return builder
            .baseUrl("https://api.infomaniak.com/1/ai/$productId/openai/chat/completions")
            .defaultHeader("Content-Type", "application/json")
            .defaultHeader("Authorization", "Bearer $imApiToken")
            .build()
    }
}