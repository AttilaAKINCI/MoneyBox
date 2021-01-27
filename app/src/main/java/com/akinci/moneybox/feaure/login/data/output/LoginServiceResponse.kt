package com.akinci.moneybox.feaure.login.data.output

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginServiceResponse(
    val ActionMessage: ActionMessage,
    val InformationMessage: String,
    val Session: Session,
    val User: User
)
