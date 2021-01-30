package com.akinci.moneybox.feaure.product.detail.repository

import com.akinci.moneybox.common.helper.Resource
import com.akinci.moneybox.feaure.product.detail.data.input.PaymentServiceRequest
import com.akinci.moneybox.feaure.product.detail.data.output.PaymentServiceResponse

class FakePaymentRepository : PaymentRepository {

    /**
     * network connected, not connected.
     * response success, fail
     * **/

    private var shouldReturnServerError = false
    fun setShouldReturnServerError(value : Boolean){ shouldReturnServerError = value }

    private var shouldReturnNetworkError = false
    fun setShouldReturnNetworkError(value : Boolean){ shouldReturnNetworkError = value }

    override suspend fun makePayment(paymentServiceRequest: PaymentServiceRequest): Resource<PaymentServiceResponse> {
        if(shouldReturnNetworkError){
            return Resource.error("Couldn't reached to server. Please check your internet connection")
        }

        if(shouldReturnServerError){
            return Resource.error("Login couldn't completed because server error. For fake reasons..")
        }else{
            val response = PaymentServiceResponse(
                500.0
            )
            return Resource.success(response)
        }
    }


}