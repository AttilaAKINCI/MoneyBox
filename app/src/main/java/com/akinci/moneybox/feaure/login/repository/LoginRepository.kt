package com.akinci.moneybox.feaure.login.repository

import com.akinci.moneybox.common.helper.Resource
import com.akinci.moneybox.feaure.login.data.input.LoginServiceRequest
import com.akinci.moneybox.feaure.login.data.output.LoginServiceResponse

interface LoginRepository {
    suspend fun login(input: LoginServiceRequest) : Resource<LoginServiceResponse>
}