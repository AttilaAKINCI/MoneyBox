package com.akinci.moneybox.feaure.product.data.output.product

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AssetBox(
    val Title: String
)