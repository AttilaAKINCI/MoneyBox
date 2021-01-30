package com.akinci.moneybox.feaure.login.repository

import com.akinci.moneybox.common.helper.Resource
import com.akinci.moneybox.feaure.login.data.input.LoginServiceRequest
import com.akinci.moneybox.feaure.login.data.output.*

class FakeLoginRepository : LoginRepository {

    /**
     * network connected, not connected.
     * response success, fail
     * **/

    private var shouldReturnServerError = false
    fun setShouldReturnServerError(value : Boolean){ shouldReturnServerError = value }

    private var shouldReturnNetworkError = false
    fun setShouldReturnNetworkError(value : Boolean){ shouldReturnNetworkError = value }


    override suspend fun login(input: LoginServiceRequest): Resource<LoginServiceResponse> {
        if(shouldReturnNetworkError){
            return Resource.error("Couldn't reached to server. Please check your internet connection")
        }

        if(shouldReturnServerError){
            return Resource.error("Login couldn't completed because server error. For fake reasons..")
        }else{
            val response = LoginServiceResponse(
                ActionMessage(listOf(
                    Action(
                        10.0,
                        "None",
                        "Add £10",
                        "Deposit"),
                    Action(
                        2.0,
                        "None",
                        "Add £2",
                        "Deposit"),
                    Action(
                        5.0,
                        "None",
                        "Add £5",
                        "Deposit")
                    ),
                    "Little and often is the best way to save. \r\nAdd a little extra to your Moneybox.",
                    "Deposit"
                ),
                InformationMessage = "1 day until collection",
                Session(
                    "PkmcQ8o3cYKoSMq0fyZo4bgjQ84t8MSZkF0NbPUdd/0=",
                    0,
                    "aea07e07-4247-40e4-9556-28ccba3d96bf",
                    "aea07e07-4247-40e4-9556-28ccba3d96bf"),
                User(
                    1,
                    "Clear",
                    "Fox",
                    false,
                    29,
                    "2020-04-08T12:24:09.613",
                    true,
                    "Active",
                    false,
                    "jaeren+androidtest@moneyboxapp.com",
                    false,
                    "Jaeren",
                    false,
                    true,
                    "65daba4358d29dde9c7a453fc75fd323947e4f38fae8da2bc84204ec953a2293",
                    "166bf73be7a21bfde873dc01aec82c74575aead20077eefd6f7d55db8ef8a720",
                    "65daba4358d29dde9c7a453fc75fd323947e4f38fae8da2bc84204ec953a2293",
                    0.0,
                    "",
                    true,
                    "None",
                    "Off",
                    "Coathup",
                    0.0,
                    "07812271271",
                    0.0,
                    "IsComplete",
                    0.0,
                    1,
                    false,
                    0.0,
                    "LPUL98",
                    "IsComplete",
                    false,
                    "Off",
                    true,
                    "947002e4-4b6a-43ff-9f3d-0d939ef2dbaf"
                )
            )
            return Resource.success(response)
        }
    }

}