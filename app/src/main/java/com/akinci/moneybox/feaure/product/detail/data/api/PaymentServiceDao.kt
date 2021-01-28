package com.akinci.moneybox.feaure.product.payment.data.api

import com.akinci.moneybox.common.network.RestConfig
import com.akinci.moneybox.feaure.product.payment.data.input.PaymentServiceRequest
import com.akinci.moneybox.feaure.product.payment.data.output.PaymentServiceResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PaymentServiceDao {
    @POST(RestConfig.ENDPOINT_PAYMENT)
    suspend fun makePayment(@Body paymentServiceRequest: PaymentServiceRequest) : Response<PaymentServiceResponse>
}