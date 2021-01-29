package com.akinci.moneybox.common.helper

/** Keeps fragment - viewModel event status **/
enum class InformerStatus {
    SUCCESS,
    ERROR,
    INFO,
    LOADING,
    NO_DATA
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
        fun <T> info(message: String, data: T? = null): Informer<T> =
                Informer(
                        status = InformerStatus.INFO,
                        data = data,
                        message = message
                )
        fun <T> error(message: String, data: T? = null): Informer<T> =
            Informer(
                status = InformerStatus.ERROR,
                data = data,
                message = message
            )
        fun <T> loading(data: T? = null): Informer<T> =
            Informer(
                status = InformerStatus.LOADING,
                data = data,
                message = null
            )
        fun <T> noData(data: T? = null): Informer<T> =
            Informer(
                status = InformerStatus.NO_DATA,
                data = data,
                message = null
            )
    }
}