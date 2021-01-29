package com.akinci.moneybox.feaure.login.repository

import com.akinci.moneybox.common.helper.Resource
import com.akinci.moneybox.feaure.login.data.input.LoginServiceRequest
import com.akinci.moneybox.feaure.login.data.output.LoginServiceResponse

class FakeLoginRepository : LoginRepository {

    /**
     * network connected, not connected.
     * response success, fail
     * **/

    override suspend fun login(input: LoginServiceRequest): Resource<LoginServiceResponse> {
        return Resource.error("")
    }

}