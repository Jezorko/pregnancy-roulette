package shared

import api.ApiClient
import jezorko.github.pregnancyroulette.PregnancyOutcome
import kotlin.js.Promise
import kotlin.random.Random

private fun unfilteredRandomOutcomesFrom(outcomes: List<PregnancyOutcome>): List<PregnancyOutcome> {
    return outcomes.shuffled(Random)
        .filter { outcome -> outcome.parsedChance.test() }
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
        }
}