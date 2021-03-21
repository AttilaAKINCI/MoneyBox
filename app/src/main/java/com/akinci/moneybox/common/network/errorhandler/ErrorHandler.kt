package com.akinci.moneybox.common.network.errorhandler

import retrofit2.Response

interface ErrorHandler {
    fun <T> parseError(response: Response<T>) : ApiErrorResponse?
}