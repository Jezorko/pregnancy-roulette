package components

import jezorko.github.pregnancyroulette.PregnancyOutcome
import react.FC
import react.Props
import react.dom.html.ReactHTML.div

external interface RandomPregnancyOutcomesListProps : Props {
    var outcomes: List<PregnancyOutcome>
}

val RandomPregnancyOutcomesList = FC<RandomPregnancyOutcomesListProps> { props ->

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
                }
            }
        }
    }
}