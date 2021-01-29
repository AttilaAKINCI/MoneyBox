package com.akinci.moneybox.feaure.login.viewmodel

import com.akinci.moneybox.common.storage.FakeLocalPreferences
import com.akinci.moneybox.feaure.login.repository.FakeLoginRepository
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class LoginViewModelTest {

    private lateinit var loginViewModel : LoginViewModel

    @Before
    fun setup(){
        loginViewModel = LoginViewModel(FakeLoginRepository(), FakeLocalPreferences())
    }

    @Test
    fun `asd`(){

      //  assertThat()
    }

    @After
    fun tearDown(){

    }

}