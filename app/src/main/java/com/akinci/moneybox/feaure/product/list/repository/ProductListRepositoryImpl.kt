package com.akinci.moneybox.feaure.product.list.repository

import com.akinci.moneybox.common.helper.Resource
import com.akinci.moneybox.common.network.NetworkChecker
import com.akinci.moneybox.common.network.errorhandler.ErrorHandler
import com.akinci.moneybox.feaure.product.list.data.api.ProductListServiceDao
import com.akinci.moneybox.feaure.product.list.data.output.ProductListServiceResponse
import kotlinx.coroutines.delay
import timber.log.Timber
import javax.inject.Inject

class ProductListRepositoryImpl @Inject constructor(
        private val productListServiceDao: ProductListServiceDao,
        private val networkChecker: NetworkChecker,
        private val restErrorHandler: ErrorHandler
) : ProductListRepository {

    override suspend fun getProductList() : Resource<ProductListServiceResponse> {
        return try {
            if (networkChecker.isNetworkConnected()) {
                //internet connection is established.
                val response = productListServiceDao.getProductList()
                if(response.isSuccessful){
                    /** 200 -> 299 Error status range **/
                    response.body()?.let {

                        delay(1000) // simulate more network delay.

                        return@let Resource.success(it)
                    } ?: Resource.error("Product list service response body is null",null)
                }else{
                    /** 400 -> 599 Error status range **/
                    val errorResponse = restErrorHandler.parseError(response)

                    Resource.error("Product list couldn't be fetched. \n" +
                            "\nReason : ${errorResponse.Name}" +
                            "\nMessage :  ${errorResponse.Message}",null)
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