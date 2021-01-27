package com.akinci.moneybox.feaure.product.data.output.product

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Product(
    val AnnualLimit: Double,
    val BonusMultiplier: Double,
    val CanWithdraw: Boolean,
    val CategoryType: String,
    val DepositLimit: Double,
    val Documents: Documents,
    val FriendlyName: String,
    val Fund: Fund,
    val Id: Int,
    val InterestRate: String,
    val InterestRateAmount: Double,
    val Lisa: Lisa,
    val LogoUrl: String,
    val MaximumWeeklyDeposit: Double,
    val MinimumWeeklyDeposit: Double,
    val Name: String,
    val ProductHexCode: String,
    val State: String,
    val Type: String
)