package com.app.zovent.ui.main.view_model

import android.util.Patterns
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.app.zovent.R
import com.app.zovent.data.api.RetrofitBuilder
import com.app.zovent.data.model.forgot_password.request.ForgotPasswordRequest
import com.app.zovent.data.model.forgot_password.response.ForgotPasswordResponse
import com.app.zovent.data.model.signin.request.LoginRequest
import com.app.zovent.data.model.signin.response.LoginResponse
import com.app.zovent.data.repository.MainRepository
import com.app.zovent.ui.base.BaseViewModel
import com.app.zovent.ui.main.fragment.ForgotPasswordFragmentDirections
import com.app.zovent.utils.Resource
import com.app.zovent.utils.StatusCode
import com.app.zovent.utils.network.Event
import kotlinx.coroutines.launch
import java.io.IOException
import kotlin.text.trim

class ForgotPasswordViewModel: BaseViewModel() {
    val email = ObservableField<String>()
    private val _validationMessage = MutableLiveData<String>()
    val validationMessage: LiveData<String> = _validationMessage

    val forgotPasswordResponse = MutableLiveData<Event<Resource<ForgotPasswordResponse>>>()


    fun onClick(view: View) {
        when(view.id) {
            R.id.sendOtpButton -> onSubmit(view)
            R.id.backButton -> view.findNavController().popBackStack()
        }
    }

    fun onSubmit(view: View) {
        val emailInput = email.get()?.trim()

        if (emailInput.isNullOrEmpty()) {
            _validationMessage.value = "Please enter email"
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            _validationMessage.value = "Please enter a valid email"
            return
        }

        // Success: hit API or navigate
//         _validationMessage.value = "Successful!"
        hitApi(ForgotPasswordRequest(email = emailInput))
    }

    fun hitApi(request: ForgotPasswordRequest) {
        val mainRepository = MainRepository(RetrofitBuilder.apiService)
        viewModelScope.launch {
            forgotPasswordResponse.postValue(Event(Resource.loading(null)))
            try {
                val result = mainRepository.forgotPasswordApi(request)
                forgotPasswordResponse.postValue(Event(Resource.success(result)))
            } catch (ex: IOException) {
                forgotPasswordResponse.postValue(
                    Event(Resource.error(StatusCode.STATUS_CODE_INTERNET_VALIDATION, null))
                )
            } catch (exception: Exception) {
                forgotPasswordResponse.postValue(
                    Event(Resource.error(StatusCode.SERVER_ERROR_MESSAGE, null))
                )
            }
        }
    }

}