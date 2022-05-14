package jezorko.github.pregnancyroulette

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.toml.TomlMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*

val json = ObjectMapper().registerKotlinModule()
val toml = TomlMapper().registerKotlinModule()

suspend fun <T> ApplicationCall.respondJson(
    status: HttpStatusCode? = null,
    provider: () -> T
) {
    respondText(
        contentType = ContentType.Application.Json,
        status = status,
        provider = { json.writeValueAsString(provider()) }
    )
}