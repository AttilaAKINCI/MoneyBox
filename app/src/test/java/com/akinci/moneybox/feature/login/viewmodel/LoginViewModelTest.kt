package com.akinci.moneybox.feature.login.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.akinci.moneybox.common.helper.InformerStatus
import com.akinci.moneybox.common.storage.LocalPreferenceConfig
import com.akinci.moneybox.getOrAwaitValueTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginViewModelTest {

    private lateinit var loginViewModel : LoginViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var fakeLocalPreferences: FakeLocalPreferences
    private lateinit var fakeLoginRepository: FakeLoginRepository

    @Before
    fun setup(){
        fakeLocalPreferences =  FakeLocalPreferences()
        fakeLoginRepository = FakeLoginRepository()
        loginViewModel = LoginViewModel(fakeLoginRepository, fakeLocalPreferences)
    }

    @Test
    fun `attempt login with empty inputs returns error`(){

        loginViewModel.email.value = ""
        loginViewModel.password.value = ""
        loginViewModel.name.value = ""

        loginViewModel.login()

        val loginEventHandlerResp = loginViewModel.loginEventHandler.getOrAwaitValueTest()

        assertThat(loginEventHandlerResp.status).isEqualTo(InformerStatus.ERROR)
    }

    @Test
    fun `attempt login with empty name returns ok`(){

        loginViewModel.email.value = "jaeren+androidtest@moneyboxapp.com"
        loginViewModel.password.value = "P455word12"
        loginViewModel.name.value = ""  // <---- optional field

        loginViewModel.login()

        val loginEventHandlerResp = loginViewModel.loginEventHandler.getOrAwaitValueTest()

        assertThat(loginEventHandlerResp.status).isEqualTo(InformerStatus.SUCCESS)
    }

    @Test
    fun `attempt login and check stored values that they are not empty`(){

        loginViewModel.email.value = "jaeren+androidtest@moneyboxapp.com"
        loginViewModel.password.value = "P455word12"
        loginViewModel.name.value = "Jaeren"  // <---- optional field

        //assume login successful because I tested this part previous test case
        loginViewModel.login()

        // if no problem with login. check shared pref inserts
        val token = fakeLocalPreferences.getStoredTag(LocalPreferenceConfig.AUTH_TOKEN)
        val userName = fakeLocalPreferences.getStoredTag(LocalPreferenceConfig.USERNAME)

        assertThat(token).isNotEmpty()
        assertThat(userName).isNotEmpty()
    }

    @Test
    fun `attempt login and get network error return error`(){
        loginViewModel.email.value = "jaeren+androidtest@moneyboxapp.com"
        loginViewModel.password.value = "P455word12"
        loginViewModel.name.value = "Jaeren"  // <---- optional field

        //network error should be fired.
        fakeLoginRepository.setShouldReturnNetworkError(true)

        //assume login successful because I tested this part previous test case
        loginViewModel.login()

        val loginEventHandlerResp = loginViewModel.loginEventHandler.getOrAwaitValueTest()

        assertThat(loginEventHandlerResp.status).isEqualTo(InformerStatus.ERROR)
        assertThat(loginEventHandlerResp.message).contains("Couldn't reached to server")
    }

    @Test
    fun `attempt login and get server error return error`(){
        loginViewModel.email.value = "jaeren+androidtest@moneyboxapp.com"
        loginViewModel.password.value = "P455word12"
        loginViewModel.name.value = "Jaeren"  // <---- optional field

        //server error should be fired.
        fakeLoginRepository.setShouldReturnServerError(true)

        //assume login successful because I tested this part previous test case
        loginViewModel.login()

        val loginEventHandlerResp = loginViewModel.loginEventHandler.getOrAwaitValueTest()

        assertThat(loginEventHandlerResp.status).isEqualTo(InformerStatus.ERROR)
        assertThat(loginEventHandlerResp.message).contains("server error")
    }

    @After
    fun tearDown(){ /** NO NEED **/ }

}