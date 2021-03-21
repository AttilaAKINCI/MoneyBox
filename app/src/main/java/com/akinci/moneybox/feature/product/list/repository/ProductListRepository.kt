package com.akinci.moneybox.feature.product.list.repository

import com.akinci.moneybox.common.helper.Resource
import com.akinci.moneybox.feature.product.list.data.output.ProductListServiceResponse

interface ProductListRepository {
    suspend fun getProductList() : Resource<ProductListServiceResponse>
}