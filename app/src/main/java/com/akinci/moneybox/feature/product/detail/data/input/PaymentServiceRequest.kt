package com.akinci.moneybox.feature.product.detail.data.input

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PaymentServiceRequest(
    val Amount: Int,
    val InvestorProductId: Int
)