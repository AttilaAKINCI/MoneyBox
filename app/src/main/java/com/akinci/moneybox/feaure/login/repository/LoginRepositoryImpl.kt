package com.akinci.moneybox.feaure.login.repository

import com.akinci.moneybox.common.network.NetworkChecker
import com.akinci.moneybox.common.helper.Resource
import com.akinci.moneybox.common.network.errorhandler.ErrorHandler
import com.akinci.moneybox.feaure.login.data.api.LoginServiceDao
import com.akinci.moneybox.feaure.login.data.input.LoginServiceRequest
import com.akinci.moneybox.feaure.login.data.output.LoginServiceResponse
import timber.log.Timber
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginService: LoginServiceDao,
    private val networkChecker: NetworkChecker,
    private val restErrorHandler: ErrorHandler
) : LoginRepository {

    override suspend fun login(input : LoginServiceRequest): Resource<LoginServiceResponse> {
        return try {
            if (networkChecker.isNetworkConnected()) {
                //internet connection is established.

                val response = loginService.login(input)
                if(response.isSuccessful){
                    /** 200 -> 299 Error status range **/
                    response.body()?.let {
                        return@let Resource.success(it)
                    } ?: Resource.error("Login service response body is null",null)
                }else{
                    /** 400 -> 599 Error status range **/
                    val errorResponse = restErrorHandler.parseError(response)

                    Resource.error("Login couldn't completed. \n" +
                            "\nReason : ${errorResponse.Name}" +
                            "\nMessage :  ${errorResponse.Message}",null)
                }
            }else{
                // not connected to internet
                Resource.error("Couldn't reached to server. Please check your internet connection",null)
            }
        } catch (exception : Exception) {
            Timber.d(exception)
            Resource.error("Service connection error.",null)
        }
    }

}