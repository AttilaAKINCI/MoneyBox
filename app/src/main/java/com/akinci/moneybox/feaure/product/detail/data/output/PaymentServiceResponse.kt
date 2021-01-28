package com.akinci.moneybox.feaure.product.payment.data.output

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PaymentServiceResponse(
    val Moneybox: Double
)