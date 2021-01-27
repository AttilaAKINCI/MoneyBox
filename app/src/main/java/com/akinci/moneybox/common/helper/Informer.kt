package com.akinci.moneybox.common.helper

/** Keeps fragment - viewModel event status **/
enum class InformerStatus {
    SUCCESS,
    ERROR
}

/** Fragment viewModel state identifier class **/
data class Informer<out T>(val status: InformerStatus, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): Informer<T> =
            Informer(
                status = InformerStatus.SUCCESS,
                data = data,
                message = null
            )
        fun <T> error(message: String, data: T? = null): Informer<T> =
            Informer(
                status = InformerStatus.ERROR,
                data = data,
                message = message
            )
    }
}