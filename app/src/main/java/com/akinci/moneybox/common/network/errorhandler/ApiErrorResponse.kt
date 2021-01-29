package com.akinci.moneybox.common.network.errorhandler

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiErrorResponse(
    val Message: String,
    val Name: String,
    val ValidationErrors: List<Any>
) {
    constructor() : this(
            "Empty error body",
            "",
            listOf<Any>()
    )
}