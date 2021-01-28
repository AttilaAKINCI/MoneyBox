package com.akinci.moneybox.feaure.product.list.data.output

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Documents(
    val KeyFeaturesUrl: String
)