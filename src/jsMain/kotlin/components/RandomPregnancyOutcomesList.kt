package components

import jezorko.github.pregnancyroulette.PregnancyOutcome
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import shared.ShownOutcomes

external interface PregnancyOutcomesListProps : Props {
    var outcomes: List<PregnancyOutcome>
}

val PregnancyOutcomesList = FC<PregnancyOutcomesListProps> { props ->

    val outcomes = props.outcomes

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
                    isNew = ShownOutcomes.checkNew(outcome.name)
                }
            }
        }
    }
}