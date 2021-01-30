package com.akinci.moneybox.common.storage

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@HiltAndroidTest
class LocalPreferencesAndroidTest {

    // this rule provides hilt injection
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    // this rule provides coroutines process sequentially in the scope
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        hiltRule.inject() // with this command hilt injects parameters
    }

    @Inject
    @Named("test-localPreference")
    lateinit var sharedPreferences : Preferences

    @Test
    fun testInsertAndRetrieveSampleForSharedPreferences(){
        val insertValue = "insertValue"
        sharedPreferences.setStoredTag("insertedKey", insertValue)

        val fetchedValue = sharedPreferences.getStoredTag("insertedKey")

        assertThat(insertValue).isEqualTo(fetchedValue)
    }

    @After
    fun tearDown(){ /** NO NEED **/ }

}