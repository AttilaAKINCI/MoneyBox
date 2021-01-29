package com.akinci.moneybox.common.di

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.akinci.moneybox.BuildConfig
import com.akinci.moneybox.RootActivity
import com.akinci.moneybox.common.network.NetworkChecker
import com.akinci.moneybox.common.network.RestConfig
import com.akinci.moneybox.common.network.errorhandler.ErrorHandler
import com.akinci.moneybox.common.network.errorhandler.ErrorHandlerImpl
import com.akinci.moneybox.common.storage.IntentParams
import com.akinci.moneybox.common.storage.LocalPreferences
import com.akinci.moneybox.common.storage.PrefConfig
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.*
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
    fun provideNetworkChecker(@ApplicationContext context: Context) = NetworkChecker(context)

    @Provides
    @Singleton
    fun provideRetrofitErrorBodyHandler(
        retrofit: Retrofit
    ) : ErrorHandler = ErrorHandlerImpl(retrofit)

    /** END **/

    /** Shared Preferences Integration
     * START
     * **/
    @Provides
    @Singleton
    fun provideLocalPreferences(@ApplicationContext context: Context) = LocalPreferences(context)
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
    @Named("RestHttpClient")
    fun provideOkHttpClient(
            @ApplicationContext context: Context,
            sharedPreferences: LocalPreferences
    ) : OkHttpClient {
        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            // debug logging activated
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY

            //add logging interceptor
            builder.addInterceptor(logger)
        }

        return builder.addInterceptor(object : Interceptor {
                    override fun intercept(chain: Interceptor.Chain): Response {
                        val bearerToken = sharedPreferences.getStoredTag(PrefConfig.AUTH_TOKEN)

                        val newRequest = chain.request().newBuilder()
                                .addHeader("AppId", BuildConfig.APP_ID)
                                .addHeader("Content-Type", BuildConfig.CONTENT_TYPE)
                                .addHeader("appVersion", BuildConfig.APP_VERSION)
                                .addHeader("apiVersion", BuildConfig.API_VERSION)
                                .addHeader("Authorization", "Bearer $bearerToken")
                                .build();

                        val response = chain.proceed(newRequest);

                        if(response.code == 401){
                            // bearer token expired..
                            // restart navigation graph.
                            val intent = Intent(context, RootActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            intent.putExtra(IntentParams.DIRECT_LOGIN, true)
                            startActivity(context, intent, null)
                        }

                        return response
                    }
                })
                .readTimeout(100, TimeUnit.SECONDS)
                .connectTimeout(100, TimeUnit.SECONDS)
                .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
            @Named("RestHttpClient") okHttpClient: OkHttpClient,
            @Named("BaseURL") baseURL: String,
            converter: MoshiConverterFactory
    ) : Retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .client(okHttpClient)
        .addConverterFactory(converter)
        .build()
    /** END **/
}