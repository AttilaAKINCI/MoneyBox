package com.akinci.moneybox.feature.product.detail.repository

import com.akinci.moneybox.common.helper.Resource
import com.akinci.moneybox.common.network.NetworkChecker
import com.akinci.moneybox.common.network.errorhandler.ErrorHandler
import com.akinci.moneybox.common.repository.BaseRepositoryImpl
import com.akinci.moneybox.feature.product.detail.data.api.PaymentServiceDao
import com.akinci.moneybox.feature.product.detail.data.input.PaymentServiceRequest
import com.akinci.moneybox.feature.product.detail.data.output.PaymentServiceResponse
import javax.inject.Inject

class PaymentRepositoryImpl@Inject constructor(
    private val paymentServiceDao: PaymentServiceDao,
    networkChecker: NetworkChecker,
    restErrorHandler : ErrorHandler
) : BaseRepositoryImpl(networkChecker, restErrorHandler), PaymentRepository {

    override suspend fun makePayment(paymentServiceRequest: PaymentServiceRequest): Resource<PaymentServiceResponse> {
        return callService { paymentServiceDao.makePayment(paymentServiceRequest) }
    }
}