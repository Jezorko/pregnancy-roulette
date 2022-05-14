package components

import kotlinx.browser.window
import react.FC
import react.Props
import react.dom.html.AnchorTarget
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import shared.attributes

external fun encodeURIComponent(input: String): String

external interface FacebookShareButtonProps : Props {
    var shareLink: String?
}

val FacebookShareButton = FC<FacebookShareButtonProps> { props ->
    val elementId = "fb-share-button"
    console.log(window.location.href)
    val shareLink = props.shareLink ?: window.location.href
    div {
        id = elementId
        className = elementId
        attributes = mapOf(
            "data-href" to encodeURIComponent(shareLink),
            "data-layout" to "button",
            "data-size" to "large"
        )
        a {
            +"Share on Facebook"
            className = "fb-xfbml-parse-ignore"
            target = AnchorTarget._blank
            href =
                "https://www.facebook.com/sharer/sharer.php?u=${
                    encodeURIComponent(shareLink)
                };src=sdkpreparse"
        }
    }
}

val SocialMediaShareButtons = FC<RandomPregnancyOutcomesListProps> {
    div {
        id = "social-media-share-buttons"
        FacebookShareButton {
            shareLink = "https://pregnancy-roulette.herokuapp.com/?outcomes=MjMsMTcsMTIsMSwxNiwxMCwxMw%3D%3D"
        }
    }
}