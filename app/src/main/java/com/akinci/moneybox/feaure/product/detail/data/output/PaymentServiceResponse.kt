package com.akinci.moneybox.feaure.product.detail.data.output

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PaymentServiceResponse(
    val Moneybox: Double
)