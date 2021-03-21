package com.akinci.moneybox.feature.product.list.data.output

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Fund(
    val FundId: Int,
    val IsFundDMB: Boolean,
    val LogoUrl: String,
    val Name: String
)