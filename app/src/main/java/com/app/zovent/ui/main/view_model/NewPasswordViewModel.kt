package com.app.zovent.ui.main.view_model

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.app.zovent.R
import com.app.zovent.data.api.RetrofitBuilder
import com.app.zovent.data.model.new_password.request.NewPasswordRequest
import com.app.zovent.data.model.verify_signup_otp.request.VerifySignupOtpRequest
import com.app.zovent.data.model.verify_signup_otp.response.VerifySignupOtpResponse
import com.app.zovent.data.repository.MainRepository
import com.app.zovent.ui.base.BaseViewModel
import com.app.zovent.utils.Resource
import com.app.zovent.utils.StatusCode
import kotlinx.coroutines.launch
import java.io.IOException

class NewPasswordViewModel : BaseViewModel() {
    val newPassword = ObservableField<String>()
    val confirmPassword = ObservableField<String>()

    private val _validationMessage = MutableLiveData<String>()
    val validationMessage: LiveData<String> = _validationMessage

    var from = ""
    var email = ""
    var otp = ""

    var getNewPasswordResponse = MutableLiveData<Resource<VerifySignupOtpResponse>>()

    fun onClick(view: View) {
        when (view.id) {
            R.id.submitButton -> onSubmit(view)
            R.id.backButton -> view.findNavController().popBackStack()
        }
    }

    private fun onSubmit(view: View) {
        val newPasswordInput = newPassword.get()?.trim()
        val confirmPasswordInput = confirmPassword.get()?.trim()

        if (newPasswordInput.isNullOrEmpty()) {
            _validationMessage.value = "Please enter new password"
            return
        }

        if (confirmPasswordInput.isNullOrEmpty()) {
            _validationMessage.value = "Please confirm your password"
            return
        }

        if (newPasswordInput != confirmPasswordInput) {
            _validationMessage.value = "Passwords do not match"
            return
        }

        // Optional: Check password strength
        if (newPasswordInput.length < 6) {
            _validationMessage.value = "Password must be at least 6 characters"
            return
        }

        // Navigate if validation is successful
        hitCreateNewPasswordApi(NewPasswordRequest(otp = otp, email = email, new_password = newPasswordInput))
        // _validationMessage.value = "Password successfully updated!"
    }
    fun hitCreateNewPasswordApi(request: NewPasswordRequest) {
        val mainRepository = MainRepository(RetrofitBuilder.apiService)
        viewModelScope.launch {
            getNewPasswordResponse.postValue(Resource.loading(null))
            try {

                getNewPasswordResponse.postValue(
                    Resource.success(
                        mainRepository.newPasswordApi(request)

                    )
                )
            } catch (ex: IOException) {
                getNewPasswordResponse.postValue(
                    Resource.error(
                        StatusCode.STATUS_CODE_INTERNET_VALIDATION,
                        null
                    )
                )
            } catch (exception: Exception) {
                getNewPasswordResponse.postValue(
                    Resource.error(
                        StatusCode.SERVER_ERROR_MESSAGE,
                        null
                    )
                )
            }


        }

    }

}
