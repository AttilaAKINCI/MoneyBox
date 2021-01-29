package com.akinci.moneybox.feaure.product.detail.repository

import com.akinci.moneybox.common.helper.Resource
import com.akinci.moneybox.common.network.NetworkChecker
import com.akinci.moneybox.common.network.errorhandler.ErrorHandler
import com.akinci.moneybox.feaure.product.detail.data.api.PaymentServiceDao
import com.akinci.moneybox.feaure.product.detail.data.input.PaymentServiceRequest
import com.akinci.moneybox.feaure.product.detail.data.output.PaymentServiceResponse
import timber.log.Timber
import javax.inject.Inject

class PaymentRepositoryImpl@Inject constructor(
        private val paymentServiceDao: PaymentServiceDao,
        private val networkChecker: NetworkChecker,
        private val restErrorHandler : ErrorHandler
) : PaymentRepository {

    override suspend fun makePayment(paymentServiceRequest: PaymentServiceRequest): Resource<PaymentServiceResponse> {
        return try {
            if (networkChecker.isNetworkConnected()) {
                //internet connection is established.
                val response = paymentServiceDao.makePayment(paymentServiceRequest)
                if(response.isSuccessful){
                    /** 200 -> 299 Error status range **/
                    response.body()?.let {
                        return@let Resource.success(it)
                    } ?: Resource.error("Make Payment Service response body is null",null)
                }else{
                    /** 400 -> 599 Error status range **/
                    val errorResponse = restErrorHandler.parseError(response)

                    Resource.error("Payment couldn't completed. \n" +
                            "\nReason : ${errorResponse.Name}" +
                            "\nMessage :  ${errorResponse.Message}",null)
                }
            }else{
                // not connected to internet
                Resource.error("Couldn't reached to server. Please check your internet connection",null)
            }
        } catch (exception : Exception) {
            Timber.d(exception)
            Resource.error("Make Payment Service connection error.",null)
        }
    }

}