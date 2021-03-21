package com.akinci.moneybox.feature.login.di

import com.akinci.moneybox.common.network.NetworkChecker
import com.akinci.moneybox.common.network.errorhandler.ErrorHandler
import com.akinci.moneybox.feature.login.data.api.LoginServiceDao
import com.akinci.moneybox.feature.login.repository.LoginRepository
import com.akinci.moneybox.feature.login.repository.LoginRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // live as long as hole application
object LoginModule {

    /** Injection definition of login features
     * START
     * **/
    @Provides
    @Singleton
    fun provideLoginServiceDao(
            retrofit: Retrofit
    ): LoginServiceDao = retrofit.create(LoginServiceDao::class.java)

    @Provides
    @Singleton
    fun provideLoginRepository(
            loginServiceDao: LoginServiceDao,
            networkChecker: NetworkChecker,
            restErrorHandler: ErrorHandler
    ): LoginRepository = LoginRepositoryImpl(loginServiceDao, networkChecker, restErrorHandler)


}