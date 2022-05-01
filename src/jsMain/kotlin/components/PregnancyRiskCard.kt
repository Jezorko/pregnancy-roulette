package components

import jezorko.github.pregnancyroulette.PregnancyRisk
import react.FC
import react.Props
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.br
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.img

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
        div {
            id = "$prefix-${props.position}-inner"
            className = "$prefix-inner"

            div {
                id = "$prefix-${props.position}-front"
                className = "$prefix-front"
                pregnancyRisk.images.firstOrNull()?.let { image ->
                    img {
                        id = "$prefix-${props.position}-image"
                        className = "$prefix-image"
                        src = image.url
                        alt = image.description
                    }
                    div {
                        id = "$prefix-${props.position}-image-source"
                        className = "$prefix-image-source"
                        +"Source: ${image.source}"
                    }
                }
            }

            div {
                id = "$prefix-${props.position}-back"
                className = "$prefix-back"
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
                        br {}
                        pregnancyRisk.references.forEachIndexed { index, reference ->
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
    }
}