package com.akinci.moneybox.feature.login.data.output

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Session(
    val BearerToken: String,
    val ExpiryInSeconds: Int,
    val ExternalSessionId: String,
    val SessionExternalId: String
)