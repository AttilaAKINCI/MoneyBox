package com.akinci.moneybox.common.storage

import androidx.test.filters.SmallTest
import com.akinci.moneybox.di.TestAppModule
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@SmallTest
@HiltAndroidTest
class LocalPreferencesAndroidTest {

    // this rule provides hilt injection
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup(){
        hiltRule.inject() // with this command hilt injects parameters
    }

    @After
    fun tearDown(){ /** NO NEED **/ }

    @Inject
    @TestAppModule.TestLocalPreference
    lateinit var sharedPreferences : Preferences

    @Test
    fun testInsertAndRetrieveSampleForSharedPreferences(){
        val insertValue = "insertValue"
        sharedPreferences.setStoredTag("insertedKey", insertValue)

        val fetchedValue = sharedPreferences.getStoredTag("insertedKey")

        assertThat(insertValue).isEqualTo(fetchedValue)
    }

}