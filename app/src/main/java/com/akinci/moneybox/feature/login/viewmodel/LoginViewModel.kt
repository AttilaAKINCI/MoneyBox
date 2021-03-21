package com.akinci.moneybox.feature.login.viewmodel

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akinci.moneybox.common.coroutine.CoroutineContextProvider
import com.akinci.moneybox.common.helper.Resource
import com.akinci.moneybox.common.storage.LocalPreferenceConfig
import com.akinci.moneybox.common.storage.Preferences
import com.akinci.moneybox.feature.login.data.input.LoginServiceRequest
import com.akinci.moneybox.feature.login.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val coroutineContext : CoroutineContextProvider,
    private val loginRepository: LoginRepository,
    private val sharedPreferences: Preferences
) : ViewModel() {

    /** validation Live Data members **/
    private val _emailValid = MutableLiveData(true)
    val emailValid : LiveData<Boolean> = _emailValid

    private val _passwordValid = MutableLiveData(true)
    val passwordValid : LiveData<Boolean> = _passwordValid

    /** sends feedback to UI layer using this event handler **/
    private val _loginEventHandler = MutableLiveData<Resource<Boolean>>()
    val loginEventHandler : LiveData<Resource<Boolean>> = _loginEventHandler

    // TODO only test purposes..... Not forget to delete initials later.
    var email = MutableLiveData("jaeren+androidtest@moneyboxapp.com")
    var password = MutableLiveData("P455word12")
    var name = MutableLiveData("Jaeren")

//    var email = MutableLiveData<String>("")
//    var password = MutableLiveData<String>("")
//    var name = MutableLiveData<String>("")

    init {
        Timber.d("LoginViewModel created..")
    }

    fun activateValidation(){
        // initially fires the validations
        if(TextUtils.isEmpty(email.value)){ email.value = "" }
        if(TextUtils.isEmpty(password.value)){ password.value = ""}
    }

    private fun validateInputFields() : Boolean {
        email.value?.let {
            if(it.isEmpty()){ _emailValid.postValue(false) }
        }

        password.value?.let {
            if(it.isEmpty()){ _passwordValid.postValue(false) }
        }

        return (_emailValid.value!! && _passwordValid.value!!)
    }

    fun login() {
        if(validateInputFields()){
            val request = LoginServiceRequest(
                email.value!!,
                password.value!!,
                "ANYTHING"
            )

            viewModelScope.launch (coroutineContext.IO) {
                when(val response = loginRepository.login(request)) {
                    is Resource.Success -> {
                        response.data?.let {
                            // login completed successfully
                            // store users name and bearer token
                            sharedPreferences.setStoredTag(LocalPreferenceConfig.AUTH_TOKEN, it.Session.BearerToken)
                            name.value?.let { username ->
                                sharedPreferences.setStoredTag(LocalPreferenceConfig.USERNAME, username)
                            }

                            // tell fragment to everything is ok and I can proceed to
                            // dashboard(products)
                            _loginEventHandler.postValue(Resource.Success(true))
                        } ?: _loginEventHandler.postValue(Resource.Error("Response data is empty"))
                    }
                    is Resource.Error -> {
                        // pass error message to fragment
                        _loginEventHandler.postValue(Resource.Error(response.message))
                    }
                }
            }
        }
    }
}