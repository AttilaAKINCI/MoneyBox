package com.akinci.moneybox.common.helper

sealed class Resource<out T> {
    data class Success<T>(val data: T?) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()
    data class Loading(val message: String = "") : Resource<Nothing>()
    data class NoData(val message: String = "") : Resource<Nothing>()
    data class Info(val message: String) : Resource<Nothing>()
}