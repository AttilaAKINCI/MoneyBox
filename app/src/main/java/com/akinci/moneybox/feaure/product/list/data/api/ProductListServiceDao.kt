package com.akinci.moneybox.feaure.product.list.data.api

import com.akinci.moneybox.common.network.RestConfig
import com.akinci.moneybox.feaure.product.list.data.output.ProductListServiceResponse
import retrofit2.Response
import retrofit2.http.GET

interface ProductListServiceDao {
    @GET(RestConfig.ENDPOINT_PRODUCTS)
    suspend fun getProductList() : Response<ProductListServiceResponse>
}