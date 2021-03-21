package com.akinci.moneybox.feature.product.list.di

import com.akinci.moneybox.common.network.NetworkChecker
import com.akinci.moneybox.common.network.errorhandler.ErrorHandler
import com.akinci.moneybox.feature.product.list.data.api.ProductListServiceDao
import com.akinci.moneybox.feature.product.list.repository.ProductListRepository
import com.akinci.moneybox.feature.product.list.repository.ProductListRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // live as long as hole application
object ProductListModule {

    /** Injection definition of product list features
     * START
     * **/
    @Provides
    @Singleton
    fun provideProductListServiceDao(
            retrofit: Retrofit
    ): ProductListServiceDao = retrofit.create(ProductListServiceDao::class.java)

    @Provides
    @Singleton
    fun provideProductListRepository(
            productListServiceDao: ProductListServiceDao,
            networkChecker: NetworkChecker,
            restErrorHandler : ErrorHandler
    ): ProductListRepository = ProductListRepositoryImpl(productListServiceDao, networkChecker, restErrorHandler)

}