package com.akinci.moneybox.feaure.product.detail.data.input

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PaymentServiceRequest(
    val Amount: Int,
    val InvestorProductId: Int
)