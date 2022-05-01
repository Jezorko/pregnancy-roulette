package jezorko.github.pregnancyroulette

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*

private val objectMapper = ObjectMapper().registerKotlinModule()

suspend fun <T> ApplicationCall.respondJson(
    status: HttpStatusCode? = null,
    provider: () -> T
) {
    respondText(
        contentType = ContentType.Application.Json,
        status = status,
        provider = { objectMapper.writeValueAsString(provider()) }
    )
}