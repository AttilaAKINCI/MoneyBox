package com.akinci.moneybox.common.di

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.akinci.moneybox.AppRootActivity
import com.akinci.moneybox.BuildConfig
import com.akinci.moneybox.common.component.FileDownloader
import com.akinci.moneybox.common.coroutine.CoroutineContextProvider
import com.akinci.moneybox.common.network.NetworkChecker
import com.akinci.moneybox.common.network.RestConfig
import com.akinci.moneybox.common.network.errorhandler.ErrorHandler
import com.akinci.moneybox.common.network.errorhandler.ErrorHandlerImpl
import com.akinci.moneybox.common.storage.IntentParams
import com.akinci.moneybox.common.storage.LocalPreferenceConfig
import com.akinci.moneybox.common.storage.Preferences
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class) // live as long as hole application
object AppModule {

    /** Coroutine context provider
     * START
     * **/
    @Provides
    @Singleton
    fun provideCoroutineContext() = CoroutineContextProvider()
    /** END **/

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
    fun provideLocalPreferences(@ApplicationContext context: Context) : Preferences = Preferences(context)
    /** END **/

    /** File Downloader Integration
     * START
     * **/
    @Provides
    @Singleton
    fun provideFileDownloader(@ApplicationContext context: Context) = FileDownloader(context)
    /** END **/

    /** Retrofit HILT Integrations
     * START
     * **/
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BaseURL

    @Provides
    @BaseURL
    fun provideBaseUrl() = RestConfig.API_SERVER_URL

    @Provides
    fun provideMoshiConverterFactory(mosh: Moshi): MoshiConverterFactory = MoshiConverterFactory.create(mosh)

    @Singleton
    @Provides
    fun providesMoshi() = Moshi.Builder().build()


    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class RestHttpClient

    @Provides
    @Singleton
    @RestHttpClient
    fun provideRestOkHttpClient(
            @ApplicationContext context: Context,
            sharedPreferences: Preferences
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

                        val bearerToken = sharedPreferences.getStoredTag(LocalPreferenceConfig.AUTH_TOKEN)

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
                            val intent = Intent(context, AppRootActivity::class.java)
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
            @RestHttpClient okHttpClient: OkHttpClient,
            @BaseURL baseURL: String,
            converter: MoshiConverterFactory
    ) : Retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .client(okHttpClient)
        .addConverterFactory(converter)
        .build()
    /** END **/
}