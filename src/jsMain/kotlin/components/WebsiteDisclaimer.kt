package components

import react.FC
import react.Props
import react.dom.html.ReactHTML.div

val WebsiteDisclaimer = FC<Props> { props ->

    div {
        id = "website-disclaimer"
        +"""DISCLAIMER: THIS WEBSITE DOES NOT PROVIDE MEDICAL ADVICE.
                The information, including but not limited to, text, graphics, images and other material container on
                this website are for informational purposes only. No material on this site is intended to be a substitute
                for professional advice, diagnosis or treatment. Always seek the advice of your physician or other
                qualified health care provider with any questions you may have regarding a medical condition or
                treatment and before undertaking a new health care regiment, and never disregard professional medical
                advice or delay in seeking it because of something you have read on this website.
                All results are completely random and therefore cannot predict the actual outcome of a pregnancy.
                Pregnant people are advised against using this website as it contains potentially distressing images.
             """.trimMargin()
    }

}