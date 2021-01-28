package com.akinci.moneybox.feaure.product.list.repository

import com.akinci.moneybox.common.helper.Resource
import com.akinci.moneybox.feaure.product.list.data.output.ProductListServiceResponse

interface ProductListRepository {
    suspend fun getProductList() : Resource<ProductListServiceResponse>
}