package com.akinci.moneybox.feaure.login.di

import com.akinci.moneybox.common.network.NetworkChecker
import com.akinci.moneybox.common.network.errorhandler.ErrorHandler
import com.akinci.moneybox.feaure.login.data.api.LoginServiceDao
import com.akinci.moneybox.feaure.login.repository.LoginRepository
import com.akinci.moneybox.feaure.login.repository.LoginRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class) // live as long as hole application
object LoginModule {

    /** Injection definition of login features
     * START
     * **/
    @Provides
    @Singleton
    @Named("LoginServiceDao")
    fun provideLoginServiceDao(
            retrofit: Retrofit
    ): LoginServiceDao = retrofit.create(LoginServiceDao::class.java)

    @Provides
    @Singleton
    fun provideLoginRepository(
            @Named("LoginServiceDao") loginServiceDao: LoginServiceDao,
            networkChecker: NetworkChecker,
            restErrorHandler: ErrorHandler
    ): LoginRepository = LoginRepositoryImpl(loginServiceDao, networkChecker, restErrorHandler)


}