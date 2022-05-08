package components

import react.FC
import react.Props
import react.dom.html.ReactHTML.div

external interface OutcomeDetectedDescriptionProps : Props {
    var numberOfNegativeOutcomes: Int?
}

val OutcomeDetectedDescription = FC<OutcomeDetectedDescriptionProps> { props ->

    val numberOfOutcomes = props.numberOfNegativeOutcomes!!
    div {
        id = "outcomes-detected-description"
        +"Ouchie, your pregnancy ended up with $numberOfOutcomes negative outcome${if (numberOfOutcomes > 1) "s" else ""}. We can always go for another one, eh?"
    }

}