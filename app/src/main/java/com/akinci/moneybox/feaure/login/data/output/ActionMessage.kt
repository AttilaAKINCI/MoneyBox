package com.akinci.moneybox.feaure.login.data.output

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ActionMessage(
    val Actions: List<Action>,
    val Message: String,
    val Type: String
)