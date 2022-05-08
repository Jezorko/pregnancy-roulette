package components

import jezorko.github.pregnancyroulette.PregnancyRiskImage
import react.FC
import react.Props
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.img
import react.useState

external interface ImageGalleryProps : Props {
    var position: Int
    var images: List<PregnancyRiskImage>
}

val ImageGallery = FC<ImageGalleryProps> { props ->
    var isEnlarged: Boolean by useState(false)
    var currentFullscreenImageIndex: Int by useState(0)

    val idPrefix = "image-gallery"
    div {
        id = "$idPrefix-${props.position}"
        className = idPrefix
        if (props.images.isNotEmpty()) {
            img {
                val image = props.images.first()
                id = "$idPrefix-${props.position}-main-image"
                className = "$idPrefix-image"
                src = image.url
                alt = "${image.description} Source: ${image.source}"
                onClick = { isEnlarged = true }
            }
            if (isEnlarged) {
                val image = props.images[currentFullscreenImageIndex]
                div {
                    id = "$idPrefix-${props.position}-fullscreen-wrapper"
                    className = "$idPrefix-fullscreen-wrapper"
                    onClick = { isEnlarged = false }

                    div {
                        id = "$idPrefix-${props.position}-image-$currentFullscreenImageIndex-container"
                        className = "$idPrefix-image-container"
                        div {
                            +"${currentFullscreenImageIndex + 1} / ${props.images.size}"
                            id = "$idPrefix-${props.position}-image-$currentFullscreenImageIndex-number"
                            className = "$idPrefix-image-number"
                        }
                        img {
                            id = "$idPrefix-${props.position}-image-$currentFullscreenImageIndex"
                            className = "$idPrefix-fullscreen-image"
                            src = image.url
                            alt = "${image.description} Source: ${image.source}"
                            onClick = { isEnlarged = !isEnlarged }
                        }
                    }
                    div {
                        className = "$idPrefix-fullscreen-previous"
                        onClick = {
                            currentFullscreenImageIndex =
                                if (currentFullscreenImageIndex == props.images.size - 1) 0
                                else currentFullscreenImageIndex + 1
                        }
                    }
                    div {
                        className = "$idPrefix-fullscreen-next"
                        onClick = {
                            currentFullscreenImageIndex =
                                if (currentFullscreenImageIndex == 0) props.images.size - 1
                                else currentFullscreenImageIndex - 1
                        }
                    }
                    a {
                        +image.source
                        className = "$idPrefix-source"
                        href = image.source
                    }
                }
            }
        }
    }
}