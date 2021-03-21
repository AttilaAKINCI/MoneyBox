package com.akinci.moneybox.feature.product.list.data.output

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HideAccounts(
    val Enabled: Boolean,
    val IsHidden: Boolean,
    val Sequence: Int
)