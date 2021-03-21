package com.akinci.moneybox.feature.product.detail.di

import com.akinci.moneybox.common.network.NetworkChecker
import com.akinci.moneybox.common.network.errorhandler.ErrorHandler
import com.akinci.moneybox.feature.product.detail.data.api.PaymentServiceDao
import com.akinci.moneybox.feature.product.detail.repository.PaymentRepository
import com.akinci.moneybox.feature.product.detail.repository.PaymentRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // live as long as hole application
object PaymentModule {

    /** Injection definition of payment features
     * START
     * **/
    @Provides
    @Singleton
    fun providePaymentServiceDao(
            retrofit: Retrofit
    ): PaymentServiceDao = retrofit.create(PaymentServiceDao::class.java)

    @Provides
    @Singleton
    fun providePaymentRepository(
            paymentServiceDao: PaymentServiceDao,
            networkChecker: NetworkChecker,
            restErrorHandler : ErrorHandler
    ): PaymentRepository = PaymentRepositoryImpl(paymentServiceDao, networkChecker, restErrorHandler)

}