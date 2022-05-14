package shared

import kotlinx.browser.window
import react.dom.html.HTMLAttributes

/**
 * TODO: figure out a proper way to set custom attributes and delete this atrocity
 */
var HTMLAttributes<*>.attributes: Map<String, String>
    get() = emptyMap()
    set(newAttributes) {
        if (id == null) {
            throw IllegalStateException("component must have ID to set custom attributes!")
        }
        window.setTimeout({
            eval(
                """
            const element = document.getElementById('$id');
            ${
                    newAttributes.map {
                        "element.setAttribute('" + it.key + "', '" + it.value + "');"
                    }.joinToString("\n")
                }
        """
            )
        }, 100)
    }