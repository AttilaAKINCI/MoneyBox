package com.akinci.moneybox.feaure.product.detail.repository

import com.akinci.moneybox.common.network.NetworkChecker
import com.akinci.moneybox.feaure.product.detail.data.api.PaymentServiceDao
import javax.inject.Inject

class PaymentRepositoryImpl@Inject constructor(
        private val paymentServiceDao: PaymentServiceDao,
        private val networkChecker: NetworkChecker
) : PaymentRepository {

}