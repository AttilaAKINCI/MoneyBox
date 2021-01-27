package com.akinci.moneybox.feaure.login.data.api

import com.akinci.moneybox.common.network.RestConfig
import com.akinci.moneybox.feaure.login.data.input.LoginServiceRequest
import com.akinci.moneybox.feaure.login.data.output.LoginServiceResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginServiceDao {
    @POST(RestConfig.ENDPOINT_LOGIN)
    suspend fun login(@Body loginServiceRequest: LoginServiceRequest) : Response<LoginServiceResponse>
}