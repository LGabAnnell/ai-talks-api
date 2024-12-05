package ch.gab.aitalksapi

import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AiTalksApiApplication

fun main(args: Array<String>) {
    runApplication<AiTalksApiApplication>(*args)
}
