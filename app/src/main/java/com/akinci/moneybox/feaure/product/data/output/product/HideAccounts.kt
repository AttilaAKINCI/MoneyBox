package com.akinci.moneybox.feaure.product.data.output.product

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HideAccounts(
    val Enabled: Boolean,
    val IsHidden: Boolean,
    val Sequence: Int
)