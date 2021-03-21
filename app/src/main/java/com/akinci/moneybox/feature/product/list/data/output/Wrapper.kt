package com.akinci.moneybox.feature.product.list.data.output

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Wrapper(
    val EarningsAsPercentage: Double,
    val EarningsNet: Double,
    val Id: String,
    val TotalContributions: Double,
    val TotalValue: Double
)