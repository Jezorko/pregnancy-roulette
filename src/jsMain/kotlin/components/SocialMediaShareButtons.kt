package components

import jezorko.github.pregnancyroulette.Constants
import jezorko.github.pregnancyroulette.PregnancyOutcome
import kotlinx.browser.window
import react.FC
import react.Props
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.img

external fun encodeURIComponent(input: String): String

external interface SocialMediaShareButtonProps : Props {
    var serviceName: String?
    var popupUrl: String?
    var imageUrl: String?
}

val SocialMediaShareButton = FC<SocialMediaShareButtonProps> { props ->
    val idSuffix = "share-button"
    val elementId = "${props.serviceName?.lowercase()}-$idSuffix"
    button {
        id = elementId
        className = "social-media-$idSuffix"
        onClick = {
            window.open(
                props.popupUrl!!,
                "pop",
                "width=600, height=400, scrollbars=no"
            )
        }
        img {
            id = "$elementId-image"
            className = "social-media-$idSuffix-image"
            src = props.imageUrl
            alt = "Share on ${props.serviceName}"
        }
    }
}

external interface SocialMediaShareButtonsProps : Props {
    var outcomes: List<PregnancyOutcome>?
}

val SocialMediaShareButtons = FC<SocialMediaShareButtonsProps> { props ->
    div {
        +"Share on socials: "
        id = "social-media-share-buttons"

        val shareLink = encodeURIComponent(window.location.href.substringBefore("?"))
        val description = encodeURIComponent(Constants.applicationDescription(props.outcomes))
        SocialMediaShareButton {
            serviceName = "Facebook"
            popupUrl = "https://www.facebook.com/sharer/sharer.php?u=$shareLink"
            imageUrl = "https://facebook.com/favicon.ico"
        }
        SocialMediaShareButton {
            serviceName = "Twitter"
            popupUrl =
                "https://twitter.com/intent/tweet?text=$description&url=$shareLink&hashtags=${Constants.HASHTAGS}"
            imageUrl = "https://twitter.com/favicon.ico"
        }
        SocialMediaShareButton {
            serviceName = "Reddit"
            popupUrl = "https://www.reddit.com/submit?url=$shareLink&title=$description"
            imageUrl = "https://www.reddit.com/favicon.ico"
        }
    }
}