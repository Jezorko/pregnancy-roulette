package components

import jezorko.github.pregnancyroulette.Constants
import jezorko.github.pregnancyroulette.PregnancyOutcome
import jezorko.github.pregnancyroulette.outcomesParamName
import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.url.URLSearchParams
import react.FC
import react.Props
import react.useState
import shared.outcomesSerializerPromise
import shared.randomOutcomes

val PregnancyRoulettePage = FC<Props> {
    val urlParams = URLSearchParams(window.location.search)
    var currentOutcomesState: List<PregnancyOutcome>? by useState(null)
    if (currentOutcomesState == null) {
        outcomesSerializerPromise.then { outcomesSerializer ->
            urlParams.get(outcomesParamName)?.let(outcomesSerializer::deserializeOutcomes)?.also {
                currentOutcomesState = it
            }
        }
    }
    val currentOutcomes = currentOutcomesState

    if (currentOutcomes != null) {
        Confetti { }
    }

    GetPregnantButton {
        onClick = {
            randomOutcomes().then { newOutcomes ->
                outcomesSerializerPromise.then { outcomesSerializer ->
                    val serializedOutcomes = outcomesSerializer.serializeOutcomes(newOutcomes)
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
        if (currentOutcomes.find { it.isNegative } != null) {
            NegativeOutcomeDetectedDescription {
                numberOfNegativeOutcomes = currentOutcomes.filter { it.isNegative }.size
            }
        } else {
            PositiveOutcomeDetectedDescription {}
        }

        PregnancyOutcomesList {
            this.outcomes = currentOutcomes
        }
    }

    SocialMediaShareButtons { outcomes = currentOutcomes }

    WebsiteDisclaimer {}

    Footer {}
}