package com.akinci.moneybox.common.helper

/** Keeps network request status during kotlin coroutine calls **/
enum class ResourceStatus {
    SUCCESS,
    ERROR,
    LOADING
}

/** Network request state identifier class **/
data class Resource<out T>(val resourceStatus: ResourceStatus, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): Resource<T> =
                Resource(
                        resourceStatus = ResourceStatus.SUCCESS,
                        data = data,
                        message = null
                )
        fun <T> error( message: String, data: T? = null): Resource<T> =
                Resource(
                        resourceStatus = ResourceStatus.ERROR,
                        data = data,
                        message = message
                )
        fun <T> loading(data: T? = null): Resource<T> =
                Resource(
                        resourceStatus = ResourceStatus.LOADING,
                        data = data,
                        message = null
                )
    }
}