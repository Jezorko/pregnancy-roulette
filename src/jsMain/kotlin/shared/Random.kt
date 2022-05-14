package shared

import api.ApiClient
import jezorko.github.pregnancyroulette.PregnancyOutcome
import jezorko.github.pregnancyroulette.healthyBaby
import kotlin.js.Promise
import kotlin.random.Random

private fun unfilteredRandomOutcomesFrom(outcomes: List<PregnancyOutcome>): List<PregnancyOutcome> {
    return outcomes.shuffled(Random)
        .filter { outcome -> outcome.chance >= Random.nextDouble(0.0, 1.0) }
        .flatMap { outcome ->
            listOf(outcome) + unfilteredRandomOutcomesFrom(outcome.consecutiveOutcomes)
        }
}

fun randomOutcomes(): Promise<List<PregnancyOutcome>> = ApiClient.allOutcomes.then { randomOutcomes(it) }

fun randomOutcomes(outcomes: List<PregnancyOutcome>): List<PregnancyOutcome> {
    return unfilteredRandomOutcomesFrom(outcomes)
        .let { selectedOutcomes ->
            selectedOutcomes.foldRight(selectedOutcomes) { currentOutcome, filteredOutcomes ->
                filteredOutcomes.filter { outcome ->
                    outcome == currentOutcome || outcome.tags.own.none(currentOutcome.tags.excludes::contains)
                }
            }
        }.ifEmpty { listOf(healthyBaby) }
}