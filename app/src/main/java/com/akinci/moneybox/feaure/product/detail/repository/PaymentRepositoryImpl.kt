package com.akinci.moneybox.feaure.product.payment.repository

import com.akinci.moneybox.common.network.NetworkChecker
import com.akinci.moneybox.feaure.product.payment.data.api.PaymentServiceDao
import javax.inject.Inject

class PaymentRepositoryImpl@Inject constructor(
        private val paymentServiceDao: PaymentServiceDao,
        private val networkChecker: NetworkChecker
) : PaymentRepository {

}