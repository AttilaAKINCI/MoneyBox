package com.akinci.moneybox.feature.login.viewmodel

import androidx.lifecycle.Observer
import com.akinci.moneybox.ahelpers.InstantExecutorExtension
import com.akinci.moneybox.ahelpers.TestContextProvider
import com.akinci.moneybox.common.helper.Resource
import com.akinci.moneybox.common.storage.Preferences
import com.akinci.moneybox.feature.login.data.output.LoginServiceResponse
import com.akinci.moneybox.feature.login.repository.LoginRepository
import com.akinci.moneybox.jsonresponses.GetLoginResponse
import com.google.common.truth.Truth.assertThat
import com.squareup.moshi.Moshi
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class LoginViewModelTest {

    @MockK
    private lateinit var loginRepository : LoginRepository

    @MockK
    private lateinit var sharedPreferences : Preferences

    private lateinit var loginViewModel : LoginViewModel

    private val coroutineContext = TestContextProvider()
    private val moshi = Moshi.Builder().build()

    @BeforeEach
    fun setup(){
        MockKAnnotations.init(this, relaxUnitFun = true)
        loginViewModel = LoginViewModel(coroutineContext, loginRepository, sharedPreferences)
    }

    @AfterEach
    fun tearDown() { unmockkAll() }

    @Test
    fun `validate input fields with empty email returns false`(){
        loginViewModel.email.value = ""
        loginViewModel.password.value ="P455word12"

        val emailObserver = mockk<Observer<Boolean>>(relaxed = true)
        val emailSlot = slot<Boolean>()

        val passwordObserver = mockk<Observer<Boolean>>(relaxed = true)
        val passwordSlot = slot<Boolean>()

        loginViewModel.emailValid.observeForever(emailObserver)
        loginViewModel.passwordValid.observeForever(passwordObserver)

        val isValid = loginViewModel.validateInputFields()

        verify { emailObserver.onChanged(capture(emailSlot)) }
        verify { passwordObserver.onChanged(capture(passwordSlot)) }

        val emailCapturedValue = emailSlot.captured
        val passwordCapturedValue = passwordSlot.captured

        assertThat(emailCapturedValue).isFalse()
        assertThat(passwordCapturedValue).isTrue()
        assertThat(isValid).isFalse()
    }

    @Test
    fun `validate input fields with empty password returns false`(){
        loginViewModel.email.value = "jaeren+androidtest@moneyboxapp.com"
        loginViewModel.password.value =""

        val emailObserver = mockk<Observer<Boolean>>(relaxed = true)
        val emailSlot = slot<Boolean>()

        val passwordObserver = mockk<Observer<Boolean>>(relaxed = true)
        val passwordSlot = slot<Boolean>()

        loginViewModel.emailValid.observeForever(emailObserver)
        loginViewModel.passwordValid.observeForever(passwordObserver)

        val isValid = loginViewModel.validateInputFields()

        verify { emailObserver.onChanged(capture(emailSlot)) }
        verify { passwordObserver.onChanged(capture(passwordSlot)) }

        val emailCapturedValue = emailSlot.captured
        val passwordCapturedValue = passwordSlot.captured

        assertThat(emailCapturedValue).isTrue()
        assertThat(passwordCapturedValue).isFalse()
        assertThat(isValid).isFalse()
    }

    @Test
    fun `validate input fields with any email and password returns true`(){
        loginViewModel.email.value = "jaeren+androidtest@moneyboxapp.com"
        loginViewModel.password.value ="P455word12"

        val emailObserver = mockk<Observer<Boolean>>(relaxed = true)
        val emailSlot = slot<Boolean>()

        val passwordObserver = mockk<Observer<Boolean>>(relaxed = true)
        val passwordSlot = slot<Boolean>()

        loginViewModel.emailValid.observeForever(emailObserver)
        loginViewModel.passwordValid.observeForever(passwordObserver)

        val isValid = loginViewModel.validateInputFields()

        verify { emailObserver.onChanged(capture(emailSlot)) }
        verify { passwordObserver.onChanged(capture(passwordSlot)) }

        val emailCapturedValue = emailSlot.captured
        val passwordCapturedValue = passwordSlot.captured

        assertThat(emailCapturedValue).isTrue()
        assertThat(passwordCapturedValue).isTrue()
        assertThat(isValid).isTrue()
    }

    @Test
    fun `attempt successful login returns ok`(){
        loginViewModel.email.value = "jaeren+androidtest@moneyboxapp.com"
        loginViewModel.password.value = "P455word12"
        loginViewModel.name.value = ""  // <---- optional field

        coEvery { loginRepository.login(any()) } returns Resource.Success(
            moshi.adapter(LoginServiceResponse::class.java).fromJson(GetLoginResponse.loginResponse)
        )

        val observer = mockk<Observer<Resource<Boolean>>>(relaxed = true)
        val slot = slot<Resource<Boolean>>()

        loginViewModel.loginEventHandler.observeForever(observer)

        loginViewModel.login()

        verify { observer.onChanged(capture(slot)) }
        val value = slot.captured as Resource.Success
        assertThat(value.data).isTrue()

        verify (atLeast = 1) { sharedPreferences.setStoredTag(any(), any()) }
    }

    @Test
    fun `attempt login with empty response body returns error`(){
        loginViewModel.email.value = "jaeren+androidtest@moneyboxapp.com"
        loginViewModel.password.value = "P455word12"
        loginViewModel.name.value = ""  // <---- optional field

        coEvery { loginRepository.login(any()) } returns Resource.Success(null)

        val observer = mockk<Observer<Resource<Boolean>>>(relaxed = true)
        val slot = slot<Resource<Boolean>>()

        loginViewModel.loginEventHandler.observeForever(observer)

        loginViewModel.login()

        verify { observer.onChanged(capture(slot)) }
        val value = slot.captured as Resource.Error
        assertThat(value.message).isEqualTo("Response data is empty")
    }

    @Test
    fun `attempt login and get server error return error`(){
        loginViewModel.email.value = "jaeren+androidtest@moneyboxapp.com"
        loginViewModel.password.value = "P455word12"
        loginViewModel.name.value = "Jaeren"  // <---- optional field

        coEvery { loginRepository.login(any()) } returns Resource.Error("Login Service encounter an error")

        val observer = mockk<Observer<Resource<Boolean>>>(relaxed = true)
        val slot = slot<Resource<Boolean>>()

        loginViewModel.loginEventHandler.observeForever(observer)

        loginViewModel.login()

        verify { observer.onChanged(capture(slot)) }
        val value = slot.captured as Resource.Error
        assertThat(value.message).isEqualTo("Login Service encounter an error")
    }

}