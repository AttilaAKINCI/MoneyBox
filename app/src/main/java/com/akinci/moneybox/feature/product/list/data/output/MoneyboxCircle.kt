package com.akinci.moneybox.feature.product.list.data.output

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MoneyboxCircle(
    val State: String
)