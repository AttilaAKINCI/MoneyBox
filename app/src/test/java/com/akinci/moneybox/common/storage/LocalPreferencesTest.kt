package com.akinci.moneybox.common.storage

import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class LocalPreferencesTest {

    private lateinit var preferences : FakeLocalPreferences

    @Before
    fun setup(){
        preferences = FakeLocalPreferences()
    }

    @Test
    fun `insert a value and then get different value, returns error`(){
        val insertValue = "insertValue"
        preferences.setStoredTag("insertedKey", insertValue)

        val fetchedValue = preferences.getStoredTag("insertedSecondKey")

        assertThat(insertValue).isNotEqualTo(fetchedValue)
    }

    @Test
    fun `insert a value and then get same value`(){
        val insertValue = "insertValue"
        preferences.setStoredTag("insertedKey", insertValue)

        val fetchedValue = preferences.getStoredTag("insertedKey")

        assertThat(insertValue).isEqualTo(fetchedValue)
    }

    @After
    fun tearDown(){ /** Not needed **/ }
}