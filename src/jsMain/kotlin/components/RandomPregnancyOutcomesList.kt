package components

import api.ApiClient
import jezorko.github.pregnancyroulette.PregnancyOutcome
import jezorko.github.pregnancyroulette.healthyBaby
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.useState
import kotlin.random.Random

external interface RandomPregnancyOutcomesListProps : Props {
    var resultId: Int?
}

val allOutcomesPromise = ApiClient.getAllOutcomes()

fun randomOutcomesFrom(random: Random, outcomes: List<PregnancyOutcome>): List<PregnancyOutcome> {
    return outcomes.shuffled(random)
        .filter { outcome -> outcome.chance >= random.nextDouble(0.0, 1.0) }
        .flatMap { outcome ->
            listOf(outcome) + randomOutcomesFrom(random, outcome.consecutiveOutcomes)
        }
}

val RandomPregnancyOutcomesList = FC<RandomPregnancyOutcomesListProps> { props ->

    var latestResultId: Int? by useState(null)
    var outcomes: List<PregnancyOutcome> by useState(emptyList())

    val resultId = props.resultId
    if (resultId != null && resultId != latestResultId) {
        latestResultId = resultId
        val random = Random(resultId)

        allOutcomesPromise.then { allOutcomes ->
            val selectedOutcomes = randomOutcomesFrom(random, allOutcomes).ifEmpty { listOf(healthyBaby) }
            val filteredOutcomes = selectedOutcomes.foldRight(selectedOutcomes) { currentOutcome, filteredOutcomes ->
                filteredOutcomes.filter { outcome ->
                    outcome == currentOutcome || outcome.tags.own.none(currentOutcome.tags.excludes::contains)
                }
            }
            outcomes = filteredOutcomes
        }
    }

    if (outcomes.isNotEmpty() && outcomes.first() != healthyBaby) {
        OutcomeDetectedDescription { numberOfNegativeOutcomes = outcomes.filter { it.isNegative }.size }
    }

    val idPrefix = "random-pregnancy-outcomes-list"
    div {
        id = idPrefix
        outcomes.forEachIndexed { index, outcome ->
            div {
                id = "$idPrefix-card-$index-wrapper"
                className = "$idPrefix-card-wrapper"
                PregnancyOutcomeCard {
                    position = index
                    pregnancyOutcome = outcome
                }
            }
        }
    }
}