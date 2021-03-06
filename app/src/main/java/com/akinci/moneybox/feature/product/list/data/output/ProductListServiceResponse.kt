package com.akinci.moneybox.feature.product.list.data.output

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductListServiceResponse(
        val Accounts: List<Account>,
        val MoneyboxEndOfTaxYear: String,
        val ProductResponses: List<ProductResponse>,
        val TotalContributionsNet: Double,
        val TotalEarnings: Double,
        val TotalEarningsAsPercentage: Double,
        val TotalPlanValue: Double
)