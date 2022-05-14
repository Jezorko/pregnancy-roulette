package components

import react.FC
import react.Props
import react.dom.html.ReactHTML.canvas


val Confetti = FC<Props> { _ ->
    val canvasId = "confetti-canvas"
    canvas {
        id = canvasId
        className = canvasId

        fun showConfetti() {
            eval(
                """
            if (!window.myConfetti) {
                window.myConfetti = confetti.create(document.getElementById('$canvasId'), {
                    resize: true,
                    useWorker: true,
                    disableForReducedMotion: true
                })                
            }
            window.myConfetti({
                particleCount: 100,
                spread: 160
            }).then(() => window.myConfetti.reset());
            """
            )
        }

        showConfetti()
    }

}