package com.akinci.moneybox.common.repository

import com.akinci.moneybox.common.helper.Resource
import com.akinci.moneybox.common.network.NetworkChecker
import com.akinci.moneybox.common.network.errorhandler.ErrorHandler
import retrofit2.Response
import timber.log.Timber

open class BaseRepositoryImpl constructor(
    private val networkChecker: NetworkChecker,
    private val restErrorHandler: ErrorHandler
) {
    /** Service generic network checker **/

    // CallService generify for JSON Responses
    suspend fun <T> callService(
            retrofitServiceAction : suspend () -> Response<T>
    ) : Resource<T> {
        return try {
            if (networkChecker.isNetworkConnected()) {
                // internet connection is established.
                // invoke service generic part.
                val response = retrofitServiceAction.invoke()
                if (response.isSuccessful) {
                    /** 200 -> 299 Error status range **/
                    response.body()?.let {
                        // handle response body
                        Resource.Success(it)
                    } ?: Resource.Error("Service response body is null")
                }else {
                    /** 400 -> 599 Error status range **/
                    Timber.d("Service response failed: %s", response.errorBody().toString())

                    val errorResponse = restErrorHandler.parseError(response)
                    Resource.Error("Service call couldn't completed. \n" +
                            "\nReason : ${errorResponse?.Name}" +
                            "\nMessage :  ${errorResponse?.Message}" +"Code: "+ response.code())
                }
            }else{
                // not connected to internet
                Timber.d("Couldn't reached to server. Please check your internet connection")
                Resource.Error("Couldn't reached to server. Please check your internet connection")
            }
        }catch (ex: Exception){
            Timber.d(ex)
            Resource.Error("UnExpected Service Exception.")
        }
    }
}