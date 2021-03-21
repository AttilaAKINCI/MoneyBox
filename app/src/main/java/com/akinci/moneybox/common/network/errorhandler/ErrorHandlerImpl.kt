package com.akinci.moneybox.common.network.errorhandler

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Inject

class ErrorHandlerImpl @Inject constructor(
    val retrofit: Retrofit
) : ErrorHandler {

    override fun <T> parseError(response: Response<T>) : ApiErrorResponse? {

        val converter: Converter<ResponseBody, ApiErrorResponse> =
            retrofit.responseBodyConverter(
                ApiErrorResponse::class.java,
                arrayOfNulls<Annotation>(0)
            )

        return response.errorBody()?.let {
            try {
                converter.convert(it)
            } catch (ex : Exception){
                Timber.d("ErrorHandlerImpl couldn't completed for : ${ex.message}")
                return null
            }
        }
    }
}