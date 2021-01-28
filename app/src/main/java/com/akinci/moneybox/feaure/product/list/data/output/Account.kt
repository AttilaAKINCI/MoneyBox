package com.akinci.moneybox.feaure.product.list.data.output

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Account(
    val Name: String,
    val Type: String,
    val Wrapper: Wrapper
)