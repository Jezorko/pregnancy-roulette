package components

import react.FC
import react.Props
import react.dom.html.ReactHTML.div


val WebsiteDescription = FC<Props> { props ->

    div {
        id = "website-description"
        +"Welcome to pregnancy roulette! Press the button to see what you can expect from your potential pregnancy."
    }

}