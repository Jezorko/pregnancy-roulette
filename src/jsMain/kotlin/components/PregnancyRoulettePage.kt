package components

import jezorko.github.pregnancyroulette.Constants
import jezorko.github.pregnancyroulette.PregnancyOutcome
import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.url.URLSearchParams
import react.FC
import react.Props
import react.useState
import shared.OutcomesSerializer
import shared.randomOutcomes

const val outcomesParamName = "outcomes"

val PregnancyRoulettePage = FC<Props> {
    val urlParams = URLSearchParams(window.location.search)
    var currentOutcomesState: List<PregnancyOutcome>? by useState(null)
    if (currentOutcomesState == null) {
        urlParams.get(outcomesParamName)?.let(OutcomesSerializer::deserializeOutcomes)?.then {
            currentOutcomesState = it
        }
    }
    val currentOutcomes = currentOutcomesState

    if (currentOutcomes != null) {
        Confetti { }
    }

    GetPregnantButton {
        onClick = {
            randomOutcomes().then { newOutcomes ->
                OutcomesSerializer.serializeOutcomes(newOutcomes).then { serializedOutcomes ->
                    urlParams.set(outcomesParamName, serializedOutcomes)
                    val newRelativePath = window.location.pathname + '?' + urlParams.toString()
                    window.history.pushState(null, "", newRelativePath)
                    currentOutcomesState = newOutcomes
                }
            }
        }
    }


    if (currentOutcomes == null) {
        WebsiteDescription {}
    } else {
        document.title = "${Constants.APPLICATION_TITLE} (${currentOutcomes.size} outcomes)"
        RandomPregnancyOutcomesList {
            this.outcomes = currentOutcomes
        }
    }

    WebsiteDisclaimer {}

    Footer {}
}