package com.akinci.moneybox.feature.product.list.repository

import com.akinci.moneybox.common.helper.Resource
import com.akinci.moneybox.common.network.NetworkChecker
import com.akinci.moneybox.common.network.errorhandler.ErrorHandler
import com.akinci.moneybox.common.repository.BaseRepositoryImpl
import com.akinci.moneybox.feature.product.list.data.api.ProductListServiceDao
import com.akinci.moneybox.feature.product.list.data.output.ProductListServiceResponse
import javax.inject.Inject

class ProductListRepositoryImpl @Inject constructor(
    private val productListServiceDao: ProductListServiceDao,
    networkChecker: NetworkChecker,
    restErrorHandler: ErrorHandler
) : BaseRepositoryImpl(networkChecker, restErrorHandler), ProductListRepository {

    override suspend fun getProductList() : Resource<ProductListServiceResponse> {
        return callService { productListServiceDao.getProductList() }
    }
}