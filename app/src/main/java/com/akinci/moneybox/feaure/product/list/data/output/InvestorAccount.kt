package com.akinci.moneybox.feaure.product.list.data.output

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class InvestorAccount(
    val ContributionsNet: Double,
    val EarningsAsPercentage: Double,
    val EarningsNet: Double,
    val TodaysInterest: Double?
)