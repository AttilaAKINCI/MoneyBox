package com.akinci.moneybox.feaure.product.detail.repository

import com.akinci.moneybox.common.helper.Resource
import com.akinci.moneybox.feaure.product.detail.data.input.PaymentServiceRequest
import com.akinci.moneybox.feaure.product.detail.data.output.PaymentServiceResponse

interface PaymentRepository {
    suspend fun makePayment(paymentServiceRequest: PaymentServiceRequest) : Resource<PaymentServiceResponse>
}