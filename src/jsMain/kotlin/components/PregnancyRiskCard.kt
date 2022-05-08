package components

import jezorko.github.pregnancyroulette.PregnancyRisk
import react.FC
import react.Props
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.br
import react.dom.html.ReactHTML.div

external interface PregnancyRiskCardProps : Props {
    var position: Int
    var pregnancyRisk: PregnancyRisk
}

val PregnancyRiskCard = FC<PregnancyRiskCardProps> { props ->
    val pregnancyRisk = props.pregnancyRisk
    val prefix = "pregnancy-risk-card"
    div {
        id = "$prefix-${props.position}"
        className = prefix

        ImageGallery {
            position = props.position
            images = props.pregnancyRisk.images
        }
        div {
            id = "$prefix-${props.position}-title"
            className = "$prefix-title"
            +pregnancyRisk.name
        }
        div {
            id = "$prefix-${props.position}-description"
            className = "$prefix-description"
            +pregnancyRisk.description
        }
        if (pregnancyRisk.references.isNotEmpty()) {
            div {
                id = "$prefix-${props.position}-references"
                className = "$prefix-references"
                +"References: "
                pregnancyRisk.references.forEachIndexed { index, reference ->
                    br {}
                    a {
                        id = "$prefix-${props.position}-reference-$index"
                        className = "$prefix-reference"
                        +"[${index + 1}] $reference"
                        href = reference.url
                    }
                }
            }
        }
    }
}