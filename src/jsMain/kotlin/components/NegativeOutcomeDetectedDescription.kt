package components

import react.FC
import react.Props
import react.dom.html.ReactHTML.div

external interface NegativeOutcomeDetectedDescriptionProps : Props {
    var numberOfNegativeOutcomes: Int?
}

val NegativeOutcomeDetectedDescription = FC<NegativeOutcomeDetectedDescriptionProps> { props ->

    val numberOfOutcomes = props.numberOfNegativeOutcomes!!
    div {
        id = "outcomes-summary-description"
        +("Ouchie, your pregnancy ended up with $numberOfOutcomes negative outcome${if (numberOfOutcomes > 1) "s" else ""}." +
                " We could always try for another one, eh?")
    }

}