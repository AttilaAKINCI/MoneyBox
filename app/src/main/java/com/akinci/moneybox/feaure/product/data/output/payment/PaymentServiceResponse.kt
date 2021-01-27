package com.akinci.moneybox.feaure.product.data.output.payment

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PaymentServiceResponse(
    val Moneybox: Double
)