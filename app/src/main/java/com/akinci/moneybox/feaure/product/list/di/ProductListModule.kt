package com.akinci.moneybox.feaure.product.list.di

import com.akinci.moneybox.common.network.NetworkChecker
import com.akinci.moneybox.feaure.product.list.data.api.ProductListServiceDao
import com.akinci.moneybox.feaure.product.list.repository.ProductListRepository
import com.akinci.moneybox.feaure.product.list.repository.ProductListRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class) // live as long as hole application
object ProductListModule {

    /** Injection definition of product list features
     * START
     * **/
    @Provides
    @Singleton
    @Named("ProductListServiceDao")
    fun provideProductListServiceDao(
            retrofit: Retrofit
    ): ProductListServiceDao = retrofit.create(ProductListServiceDao::class.java)

    @Provides
    @Singleton
    fun provideProductListRepository(
            @Named("ProductListServiceDao") productListServiceDao: ProductListServiceDao,
            networkChecker: NetworkChecker
    ): ProductListRepository = ProductListRepositoryImpl(productListServiceDao, networkChecker)

}