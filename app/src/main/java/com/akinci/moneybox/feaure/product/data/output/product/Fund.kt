package com.akinci.moneybox.feaure.product.data.output.product

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Fund(
    val FundId: Int,
    val IsFundDMB: Boolean,
    val LogoUrl: String,
    val Name: String
)