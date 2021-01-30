package com.akinci.moneybox.di

import android.content.Context
import com.akinci.moneybox.common.storage.LocalPreferences
import com.akinci.moneybox.common.storage.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Named

@Module
@InstallIn(ApplicationComponent::class)
object TestAppModule {

    @Provides
    @Named("test-localPreference")
    fun provideLocalPreferences(
        @ApplicationContext context: Context
    ) : Preferences = LocalPreferences(context)

}