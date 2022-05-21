package components

import jezorko.github.pregnancyroulette.PregnancyOutcome
import react.FC
import react.Props
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.br
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.img
import react.useState

external interface PregnancyOutcomeCardProps : Props {
    var position: Int
    var pregnancyOutcome: PregnancyOutcome
}

val PregnancyOutcomeCard = FC<PregnancyOutcomeCardProps> { props ->
    val pregnancyOutcome = props.pregnancyOutcome
    val prefix = "pregnancy-outcome-card"

    var isRevealed: Boolean by useState(false)

    div {
        id = "$prefix-${props.position}"
        className = prefix

        div {
            id = "$prefix-${props.position}-cover"
            className = "$prefix-cover ${if (isRevealed) "hidden" else "shown"}"
            img {
                id = "$prefix-${props.position}-cover-image"
                className = "$prefix-cover-image"
                src = "/static/${if (pregnancyOutcome.isNegative) "negative" else "positive"}_outcome.svg"
                alt = "${if (pregnancyOutcome.isNegative) "negative" else "positive"} outcome"
            }
            onClick = {
                isRevealed = !isRevealed
            }
        }

        div {
            id = "$prefix-${props.position}-inner"
            className = "$prefix-inner ${if (!isRevealed) "hidden" else "shown"}"

            div {
                id = "$prefix-${props.position}-front"
                className = "$prefix-front $prefix-front-${if (pregnancyOutcome.isNegative) "negative" else "positive"}"
                pregnancyOutcome.images.firstOrNull()?.let { image ->
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
                    +pregnancyOutcome.name
                }
                div {
                    id = "$prefix-${props.position}-subtitle"
                    className = "$prefix-subtitle"
                    +"(${pregnancyOutcome.chance} chance)"
                }
                div {
                    id = "$prefix-${props.position}-description"
                    className = "$prefix-description"
                    +pregnancyOutcome.description
                }
                if (pregnancyOutcome.references.isNotEmpty()) {
                    div {
                        id = "$prefix-${props.position}-references"
                        className = "$prefix-references"
                        +"References: "
                        pregnancyOutcome.references.forEachIndexed { index, reference ->
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
    }
}