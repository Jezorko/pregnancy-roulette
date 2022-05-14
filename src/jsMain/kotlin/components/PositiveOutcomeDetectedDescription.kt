package components

import react.FC
import react.Props
import react.dom.html.ReactHTML.div


val PositiveOutcomeDetectedDescription = FC<Props> { _ ->
    div {
        id = "outcomes-summary-description"
        +("Congratulations!" +
                " You were lucky enough to birth a healthy baby and have no negative outcomes." +
                " Still, remember that this list is not exhaustive and you may not be this lucky with an actual pregnancy.")
    }

}