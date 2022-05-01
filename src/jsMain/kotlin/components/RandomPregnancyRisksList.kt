package components

import api.ApiClient
import jezorko.github.pregnancyroulette.PregnancyRisk
import jezorko.github.pregnancyroulette.healthyBaby
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.useState
import kotlin.random.Random

external interface RandomPregnancyRisksListProps : Props {
    var resultId: Int?
}

val allRisksPromise = ApiClient.getAllRisks()

fun randomRisksFrom(random: Random, risks: List<PregnancyRisk>): List<PregnancyRisk> {
    return risks.shuffled(random)
        .filter { risk -> risk.chance >= random.nextDouble(0.0, 1.0) }
        .flatMap { risk ->
            listOf(risk) + randomRisksFrom(random, risk.consecutiveRisks)
        }
}

val RandomPregnancyRisksList = FC<RandomPregnancyRisksListProps> { props ->

    var latestResultId: Int? by useState(null)
    var risks: List<PregnancyRisk> by useState(emptyList())

    val resultId = props.resultId
    if (resultId != null && resultId != latestResultId) {
        latestResultId = resultId
        val random = Random(resultId)

        allRisksPromise.then { allRisks ->
            risks = randomRisksFrom(random, allRisks).ifEmpty { listOf(healthyBaby) }
        }
    }

    if (risks.isNotEmpty() && risks.first() != healthyBaby) {
        RisksDetectedDescription { numberOfRisks = risks.size }
    }

    div {
        id = "random-pregnancy-risks-list"
        risks.forEachIndexed { index, risk ->
            PregnancyRiskCard {
                position = index
                pregnancyRisk = risk
            }
        }
    }

}