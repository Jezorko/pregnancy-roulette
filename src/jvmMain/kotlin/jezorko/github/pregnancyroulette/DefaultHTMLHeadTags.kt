package jezorko.github.pregnancyroulette

import kotlinx.html.HEAD
import kotlinx.html.link
import kotlinx.html.meta

fun attachDefaultTagsTo(head: HEAD) = head.apply {
    link { rel = "stylesheet"; href = "static/styles.css" }
    link { rel = "icon"; href = "/favicon.png"; type = "image/png" }
    link { rel = "shortcut icon"; href = "/favicon.ico" }
    meta {
        name = "google-site-verification"
        content = "EB2HSryY6T7e424Mnjdq5T960Fhx1py8zMN8_yPvHHo"
    }
    meta {
        name = "viewport"
        content = "width=device-width, initial-scale=1"
    }
    meta {
        attributes["property"] = "og:title"
        content = Constants.APPLICATION_TITLE
    }
    meta {
        attributes["property"] = "og:url"
        content = Configuration.APPLICATION_URL.value
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