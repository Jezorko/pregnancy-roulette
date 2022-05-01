package components

import jezorko.github.pregnancyroulette.Constants
import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.url.URLSearchParams
import react.FC
import react.Props
import react.useState
import kotlin.random.Random

const val resultIdParam = "resultId"

val PregnancyRoulettePage = FC<Props> { props ->
    val urlParams = URLSearchParams(window.location.search)

    var resultId: Int? by useState(urlParams.get(resultIdParam)?.toIntOrNull())

    GetPregnantButton {
        onClick = {
            val randomId = Random.nextInt(from = 0, until = Int.MAX_VALUE)
            urlParams.set(resultIdParam, randomId.toString())
            val newRelativePath = window.location.pathname + '?' + urlParams.toString();
            window.history.pushState(null, "", newRelativePath);
            resultId = randomId
        }
    }

    if (resultId == null) {
        WebsiteDescription {}
    } else {
        document.title = "${Constants.APPLICATION_TITLE} #$resultId"
        RandomPregnancyRisksList {
            this.resultId = resultId
        }
    }

    WebsiteDisclaimer {}

    Footer {}
}