package components

import react.FC
import react.Props
import react.dom.html.ReactHTML.canvas

external interface ConfettiProps : Props {
    var resultId: Int?
}

val Confetti = FC<ConfettiProps> { props ->
    val canvasIdPrefix = "confetti-canvas"
    val canvasId = "$canvasIdPrefix-${props.resultId}"
    canvas {
        id = canvasId
        className = canvasIdPrefix

        fun showConfetti() {
            eval(
                """
            confetti.create(document.getElementById('$canvasId'), {
                resize: true,
                useWorker: true
            })({
                particleCount: 100,
                spread: 160
            });
            """
            )
        }

        showConfetti()
    }

}