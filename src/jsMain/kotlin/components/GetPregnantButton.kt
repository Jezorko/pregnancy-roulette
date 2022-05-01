package components

import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.img
import react.useState

external interface GetPregnantButtonProps : Props {
    var onClick: () -> Unit
}

val GetPregnantButton = FC<GetPregnantButtonProps> { props ->

    var isPressed: Boolean by useState(false)

    div {
        id = "get-pregnant-button"
        onMouseDown = { isPressed = true }
        onMouseUp = { isPressed = false }
        onClick = { props.onClick() }
        img {
            id = "get-pregnant-button-image"
            src = "static/get_pregnant_button${if (isPressed) "_pressed" else ""}.svg"
        }
    }
}