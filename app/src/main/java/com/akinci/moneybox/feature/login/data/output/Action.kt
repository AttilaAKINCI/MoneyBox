package com.akinci.moneybox.feature.login.data.output

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Action(
    val Amount: Double,
    val Animation: String,
    val Label: String,
    val Type: String
)