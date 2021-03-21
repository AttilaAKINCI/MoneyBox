package com.akinci.moneybox.feature.login.repository

import com.akinci.moneybox.common.helper.Resource
import com.akinci.moneybox.feature.login.data.input.LoginServiceRequest
import com.akinci.moneybox.feature.login.data.output.LoginServiceResponse

interface LoginRepository {
    suspend fun login(input: LoginServiceRequest) : Resource<LoginServiceResponse>
}