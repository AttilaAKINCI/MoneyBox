package com.akinci.moneybox.feaure.product.list.repository

import com.akinci.moneybox.common.helper.Resource
import com.akinci.moneybox.common.network.NetworkChecker
import com.akinci.moneybox.feaure.product.list.data.api.ProductListServiceDao
import com.akinci.moneybox.feaure.product.list.data.output.ProductListServiceResponse
import timber.log.Timber
import javax.inject.Inject

class ProductListRepositoryImpl @Inject constructor(
        private val productListServiceDao: ProductListServiceDao,
        private val networkChecker: NetworkChecker
) : ProductListRepository {

    override suspend fun getProductList() : Resource<ProductListServiceResponse> {
        return try {
            if (networkChecker.isNetworkConnected()) {
                //internet connection is established.
                val response = productListServiceDao.getProductList()
                if(response.isSuccessful){
                    response.body()?.let {
                        return@let Resource.success(it)
                    } ?: Resource.error("Product list service response body is null",null)
                }else{
                    Resource.error("Product list service is not successful",null)
                }
            } else {
                // not connected to internet
                Resource.error("Couldn't reached to server. Please check your internet connection", null)
            }
        } catch (exception : Exception) {
            Timber.d(exception)
            Resource.error("Product List Service connection error.",null)
        }
    }
}