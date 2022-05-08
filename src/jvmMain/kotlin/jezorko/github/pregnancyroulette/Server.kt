package jezorko.github.pregnancyroulette

import io.ktor.application.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.html.*

fun HTML.index() {
    head {
        title(Constants.APPLICATION_TITLE)
        link { rel = "stylesheet"; href = "static/styles.css" }
        link { rel = "icon"; href = "/favicon.png"; type = "image/png" }
        link { rel = "shortcut icon"; href = "/favicon.ico" }
        meta {
            name = "viewport"
            content = "width=device-width, initial-scale=1"
        }
        meta {
            attributes["property"] = "og:title"
            content = Constants.APPLICATION_TITLE
        }
        meta {
            name = "description"
            content = Constants.APPLICATION_DESCRIPTION
        }
        meta {
            attributes["property"] = "og:url"
            content = Configuration.APPLICATION_URL.value
        }
        meta {
            attributes["property"] = "og:description"
            content = Constants.APPLICATION_DESCRIPTION
        }
        meta {
            attributes["property"] = "og:image"
            content = Configuration.APPLICATION_IMAGE_URL.value
        }
        meta {
            attributes["property"] = "og:type"
            content = "website"
        }
        meta {
            attributes["property"] = "og:locale"
            content = "en"
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
    embeddedServer(Netty, port = Configuration.PORT.value) {
        routing {
            get("/") {
                call.respondHtml(HttpStatusCode.OK, HTML::index)
            }
            static("/") {
                resource(resource = "/static/favicon.ico", remotePath = "favicon.ico")
                resource(resource = "/static/favicon.png", remotePath = "favicon.png")
            }
            static("/static") {
                resource("pregnancy-roulette.js")
                resources("/static")
            }
        }.also { routing ->
            routing.merge(versionsRoutes())
        }
    }.start(wait = true)
}