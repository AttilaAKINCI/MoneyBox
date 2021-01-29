package com.akinci.moneybox.feaure.login.viewmodel

import android.text.TextUtils
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.NavHostFragment
import com.akinci.moneybox.common.helper.Informer
import com.akinci.moneybox.common.helper.ResourceStatus
import com.akinci.moneybox.common.storage.LocalPreferences
import com.akinci.moneybox.common.storage.PrefConfig
import com.akinci.moneybox.common.storage.Preferences
import com.akinci.moneybox.feaure.login.data.input.LoginServiceRequest
import com.akinci.moneybox.feaure.login.repository.LoginRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class LoginViewModel @ViewModelInject constructor(
        private val loginRepository: LoginRepository,
        private val sharedPreferences: Preferences
) : ViewModel() {

    private val _loginEventHandler = MutableLiveData<Informer<Boolean>>()
    val loginEventHandler : LiveData<Informer<Boolean>> = _loginEventHandler

    // TODO only test purposes..... Not forget to delete initials later.
    var email = MutableLiveData<String>("jaeren+androidtest@moneyboxapp.com")
    var password = MutableLiveData<String>("P455word12")
    var name = MutableLiveData<String>("Jaeren")

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

    fun login() {
        if(email.value!!.isEmpty() || password.value!!.isEmpty() ){
            _loginEventHandler.postValue(Informer.error("email or password should not be empty", null))
            return
        }

        val request = LoginServiceRequest(
            email.value!!,
            password.value!!,
            "ANYTHING"
        )

        viewModelScope.launch {
            val response = loginRepository.login(request)
            when(response.resourceStatus){
                ResourceStatus.SUCCESS -> {
                    response.data?.let {
                        // login completed successfully

                        // store users name and bearer token
                        sharedPreferences.setStoredTag(PrefConfig.AUTH_TOKEN, it.Session.BearerToken)
                        name.value?.let { username ->
                            sharedPreferences.setStoredTag(PrefConfig.USERNAME, username)
                        }

                        // tell fragment to everything is ok and we can proceed to
                        // dashboard(products)
                        _loginEventHandler.postValue(Informer.success(true))
                    } ?: _loginEventHandler.postValue(Informer.error("Response data is empty", null))
                }
                ResourceStatus.ERROR -> {
                    // pass error message to fragment
                    _loginEventHandler.postValue(Informer.error(response.message ?: "", null))
                }
            }
        }
    }
}