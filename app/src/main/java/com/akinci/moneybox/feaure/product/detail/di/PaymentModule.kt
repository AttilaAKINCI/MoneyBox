package com.akinci.moneybox.feaure.product.detail.di

import com.akinci.moneybox.common.network.NetworkChecker
import com.akinci.moneybox.feaure.product.detail.data.api.PaymentServiceDao
import com.akinci.moneybox.feaure.product.detail.repository.PaymentRepository
import com.akinci.moneybox.feaure.product.detail.repository.PaymentRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class) // live as long as hole application
object PaymentModule {

    /** Injection definition of payment features
     * START
     * **/
    @Provides
    @Singleton
    @Named("PaymentServiceDao")
    fun providePaymentServiceDao(
            retrofit: Retrofit
    ): PaymentServiceDao = retrofit.create(PaymentServiceDao::class.java)

    @Provides
    @Singleton
    fun providePaymentRepository(
            @Named("PaymentServiceDao") paymentServiceDao: PaymentServiceDao,
            networkChecker: NetworkChecker
    ): PaymentRepository = PaymentRepositoryImpl(paymentServiceDao, networkChecker)

}