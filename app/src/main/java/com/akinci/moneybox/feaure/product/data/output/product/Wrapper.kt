package com.akinci.moneybox.feaure.product.data.output.product

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Wrapper(
    val EarningsAsPercentage: Double,
    val EarningsNet: Double,
    val Id: String,
    val TotalContributions: Double,
    val TotalValue: Double
)