package jezorko.github.pregnancyroulette

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.util.pipeline.*

suspend fun PipelineContext<Unit, ApplicationCall>.requireAdmin(block: suspend PipelineContext<Unit, ApplicationCall>.() -> Unit) {
    if (Configuration.ADMIN_TOKEN.value == call.request.headers["X-Auth-Token"]) {
        block(this)
    } else {
        call.respondText(status = HttpStatusCode.Unauthorized) { "invalid admin token" }
    }
}

suspend fun PipelineContext<Unit, ApplicationCall>.bodyAsForm() = call.receiveText()
    .split("&")
    .associate { part ->
        part.substringBefore("=") to part.substringAfter("=")
    }
