package com.app.zovent.ui.main.view_model

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.app.zovent.R
import com.app.zovent.data.api.RetrofitBuilder
import com.app.zovent.data.model.signin.request.LoginRequest
import com.app.zovent.data.model.signin.response.LoginResponse
import com.app.zovent.data.repository.MainRepository
import com.app.zovent.ui.base.BaseViewModel
import com.app.zovent.utils.Resource
import com.app.zovent.utils.StatusCode
import kotlinx.coroutines.launch
import java.io.IOException

class SigninViewModel: BaseViewModel() {
    val email = ObservableField<String>()
    val password = ObservableField<String>()

    private val _closeActivityEvent = MutableLiveData<Unit>()
    val closeActivityEvent: LiveData<Unit> = _closeActivityEvent

    private val _validationMessage = MutableLiveData<String>()
    val validationMessage: LiveData<String> = _validationMessage

    var loginResponse = MutableLiveData<Resource<LoginResponse>>()



    fun onClick(view: View) {
        when(view.id) {
            R.id.loginButton -> onSubmit(view)
            R.id.backButton -> _closeActivityEvent.value = Unit
            R.id.forgotPassword -> view.findNavController().navigate(R.id.action_signinFragment_to_forgotPasswordFragment)
        }
    }

    fun onSubmit(view: View) {
        val usernameInput = email.get()?.trim()
        val passwordInput = password.get()?.trim()

        if (usernameInput.isNullOrEmpty()) {
            _validationMessage.value = "Please Enter Username"
            return
        }

        if (passwordInput.isNullOrEmpty()) {
            _validationMessage.value = "Please Enter Password"
            return
        }

        hitApi(LoginRequest(username = usernameInput, password = passwordInput))
    // _validationMessage.value = "Successful!"
    }
    fun hitApi(loginRequest: LoginRequest) {
        val mainRepository = MainRepository(RetrofitBuilder.apiService)
        viewModelScope.launch {
            loginResponse.postValue(Resource.loading(null))
            try {

                loginResponse.postValue(
                    Resource.success(
                        mainRepository.loginApi(loginRequest)

                    )
                )
            } catch (ex: IOException) {
                loginResponse.postValue(
                    Resource.error(
                        StatusCode.STATUS_CODE_INTERNET_VALIDATION,
                        null
                    )
                )
            } catch (exception: Exception) {
                loginResponse.postValue(
                    Resource.error(
                        StatusCode.SERVER_ERROR_MESSAGE,
                        null
                    )
                )
            }


        }

    }

}