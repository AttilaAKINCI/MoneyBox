package com.akinci.moneybox.feature.login.repository

import com.akinci.moneybox.common.helper.Resource
import com.akinci.moneybox.common.network.NetworkChecker
import com.akinci.moneybox.common.network.errorhandler.ErrorHandler
import com.akinci.moneybox.common.repository.BaseRepositoryImpl
import com.akinci.moneybox.feature.login.data.api.LoginServiceDao
import com.akinci.moneybox.feature.login.data.input.LoginServiceRequest
import com.akinci.moneybox.feature.login.data.output.LoginServiceResponse
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginService: LoginServiceDao,
    networkChecker: NetworkChecker,
    restErrorHandler: ErrorHandler
) : BaseRepositoryImpl(networkChecker, restErrorHandler), LoginRepository {

    override suspend fun login(input : LoginServiceRequest): Resource<LoginServiceResponse> {
        return callService { loginService.login(input) }
    }

}