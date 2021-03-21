package com.akinci.moneybox.feature.login.data.input

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginServiceRequest(
    val Email: String,
    val Password: String,
    val Idfa: String
)