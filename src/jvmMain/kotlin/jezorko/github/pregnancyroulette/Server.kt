package jezorko.github.pregnancyroulette

import io.ktor.application.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import jezorko.github.pregnancyroulette.database.databaseRoutes
import jezorko.github.pregnancyroulette.suggestions.outcomeSuggestionsRoutes
import kotlinx.html.*
import mu.KotlinLogging.logger

val log = logger { }

fun HTML.index(outcomes: List<PregnancyOutcome>?) {
    head {
        title(Constants.APPLICATION_TITLE)
        attachDefaultTagsTo(this)
        meta {
            name = "description"
            content = Constants.applicationDescription(outcomes)
        }
        meta {
            attributes["property"] = "og:description"
            content = Constants.applicationDescription(outcomes)
        }
    }
    body {
        div {
            id = "root"
        }
        script(src = "https://cdn.jsdelivr.net/npm/canvas-confetti@1.5.1/dist/confetti.browser.min.js") {}
        script(src = "/static/pregnancy-roulette.js") {}
    }
}

fun main() {
    log.info { "admin token is ${Configuration.ADMIN_TOKEN.value}" }
    embeddedServer(Netty, port = Configuration.PORT.value) {
        routing {
            get("/") {
                call.respondHtml(HttpStatusCode.OK) {
                    index(
                        context.request.queryParameters[outcomesParamName]
                            ?.let(outcomesSerializer::deserializeOutcomes)
                    )
                }
            }
            static("/") {
                resource(resource = "/static/favicon.ico", remotePath = "favicon.ico")
                resource(resource = "/static/favicon.png", remotePath = "favicon.png")
                resource(resource = "/static/robots.txt", remotePath = "robots.txt")
            }
            static("/static") {
                resource("pregnancy-roulette.js")
                resources("/static")
            }
        }.apply {
            merge(versionsRoutes())
            merge(outcomeSuggestionsRoutes())
            merge(databaseRoutes())
        }
    }.start(wait = true)
}