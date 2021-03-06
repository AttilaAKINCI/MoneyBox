package com.akinci.moneybox.feature.product.list.data.output

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductResponse(
    val AssetBox: AssetBox,
    val CollectionDayMessage: String,
    val Contributions: Contributions,
    val Id: Int,
    val InvestorAccount: InvestorAccount,
    val IsCashBox: Boolean,
    val IsFavourite: Boolean,
    val IsSelected: Boolean,
    var Moneybox: Double,
    val MoneyboxCircle: MoneyboxCircle,
    val Personalisation: Personalisation,
    val PlanValue: Double,
    val Product: Product,
    val State: String,
    val SubscriptionAmount: Double,
    val TotalFees: Double,
    val WrapperId: String
)