package com.akinci.moneybox.common.di

import android.content.Context
import com.akinci.moneybox.BuildConfig
import com.akinci.moneybox.common.network.NetworkHelper
import com.akinci.moneybox.common.network.RestConfig
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class) // live as long as hole application
object AppModule {
    /**
     * ApplicationComponent::class --> App Scoped
     * ActivityComponent::class --> Activity Scoped
     * FragmentComponent::class --> Fragment Scoped
     * ViewComponent::Class --> View Scoped
     * **/

    /** Network Connection Checker Integration
     * START
     * **/
    @Provides
    @Singleton
    fun provideNetworkChecker(@ApplicationContext context: Context) = NetworkHelper(context)
    /** END **/

    /** Retrofit HILT Integrations
     * START
     * **/
    @Provides
    @Named("BaseURL")
    fun provideBaseUrl() = RestConfig.API_SERVER_URL

    @Provides
    fun provideMoshiConverterFactory(mosh: Moshi): MoshiConverterFactory = MoshiConverterFactory.create(mosh)

    @Singleton
    @Provides
    fun providesMoshi() = Moshi.Builder().build()

    @Provides
    @Singleton
    fun provideOkHttpClient() : OkHttpClient =
        if (BuildConfig.DEBUG) {
            // debug logging activated
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY

            OkHttpClient.Builder()
                .addInterceptor(logger)
                .readTimeout(100, TimeUnit.SECONDS)
                .connectTimeout(100, TimeUnit.SECONDS)
                .build()
        } else {
            // debug logging activated
            OkHttpClient.Builder()
                .readTimeout(100, TimeUnit.SECONDS)
                .connectTimeout(100, TimeUnit.SECONDS)
                .build()
        }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        @Named("BaseURL") baseURL: String,
        converter : MoshiConverterFactory
    ) : Retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .client(okHttpClient)
        .addConverterFactory(converter)
        .build()
    /** END **/
}