package components

import react.FC
import react.Props
import react.dom.html.ReactHTML.div

external interface RisksDetectedDescriptionProps : Props {
    var numberOfRisks: Int?
}

val RisksDetectedDescription = FC<RisksDetectedDescriptionProps> { props ->

    val numberOfRisks = props.numberOfRisks!!
    div {
        id = "risks-detected-description"
        +"Ouchie, your pregnancy ended up with $numberOfRisks risk${if (numberOfRisks > 1) "s" else ""}. We can always go for another one, eh?"
    }

}