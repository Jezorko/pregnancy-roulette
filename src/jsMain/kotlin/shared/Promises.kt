package shared

import kotlin.js.Promise

fun <T, S> Promise<T>.flatThen(callback: (T) -> Promise<S>) = Promise<S> { resolve, reject ->
    then { callback(it).then(resolve).catch(reject) }.catch(reject)
}