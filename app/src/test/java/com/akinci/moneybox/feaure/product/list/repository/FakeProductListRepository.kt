package com.akinci.moneybox.feaure.product.list.repository

import com.akinci.moneybox.common.helper.Resource
import com.akinci.moneybox.feaure.product.list.data.output.Account
import com.akinci.moneybox.feaure.product.list.data.output.ProductListServiceResponse
import com.akinci.moneybox.feaure.product.list.data.output.Wrapper

class FakeProductListRepository : ProductListRepository{

    /**
     * network connected, not connected.
     * response success, fail
     * **/

    private var shouldReturnServerError = false
    fun setShouldReturnServerError(value : Boolean){ shouldReturnServerError = value }

    private var shouldReturnNetworkError = false
    fun setShouldReturnNetworkError(value : Boolean){ shouldReturnNetworkError = value }

    override suspend fun getProductList(): Resource<ProductListServiceResponse> {

        if(shouldReturnNetworkError){
            return Resource.error("Couldn't reached to server. Please check your internet connection")
        }

        if(shouldReturnServerError){
            return Resource.error("Login couldn't completed because server error. For fake reasons..")
        }else{
            val response = ProductListServiceResponse(
                Accounts = listOf(
                    Account(
                        "General Investment Account",
                        "Gia",
                        Wrapper(
                            5.07,
                            632.19,
                            "9443dbbf-96db-4114-9966-517e2054af32",
                            12480.0,
                            13112.19
                        )
                    )
                ),
                "2021-03-31T12:00:00.000",
                ProductResponses = listOf(
                    /** left empty for test cases. **/
                ),
                31539.81,
                1903.1,
                6.03,
                33439.22
            )
            return Resource.success(response)
        }
    }
}