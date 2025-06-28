package com.app.zovent.ui.main.view_model

import android.content.Intent
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.app.zovent.R
import com.app.zovent.data.api.RetrofitBuilder
import com.app.zovent.data.model.signup.request.SignupRequest
import com.app.zovent.data.model.signup.response.SignupResponse
import com.app.zovent.data.model.verify_signup_otp.request.VerifySignupOtpRequest
import com.app.zovent.data.model.verify_signup_otp.response.VerifySignupOtpResponse
import com.app.zovent.data.repository.MainRepository
import com.app.zovent.ui.base.BaseViewModel
import com.app.zovent.ui.main.activity.DashboardActivity
import com.app.zovent.utils.Resource
import com.app.zovent.utils.StatusCode
import com.app.zovent.utils.network.Event
import kotlinx.coroutines.launch
import java.io.IOException

class OTPViewModel : BaseViewModel() {

    val otp1 = ObservableField<String>()
    val otp2 = ObservableField<String>()
    val otp3 = ObservableField<String>()
    val otp4 = ObservableField<String>()
    val otp5 = ObservableField<String>()
    val otp6 = ObservableField<String>()

    var from = ""
    var email = ""

    private val _validationMessage = MutableLiveData<String>()
    val validationMessage: LiveData<String> get() = _validationMessage

    var getVerifySignupOtpResponse = MutableLiveData<Resource<VerifySignupOtpResponse>>()
    var getVerifyForgotPasswordOtpResponse = MutableLiveData<Event<Resource<VerifySignupOtpResponse>>>()


    fun onClick(view: View) {
        when (view.id) {
            R.id.backButton -> {
                view.findNavController().popBackStack()
            }
            R.id.verifyButton -> {
                if (!validateOtp()) return

                val enteredOtp = getEnteredOtp()

                when (from) {
                    "signup" -> {
                        hitVerifySignupOtpApi(VerifySignupOtpRequest(email = email.trim() , otp = enteredOtp))
                    }
                    "forgot" -> {
                        hitVerifyForgotPasswordOtpApi(VerifySignupOtpRequest(email = email.trim() , otp = enteredOtp))
                    }
                }
            }
        }
    }

    fun getEnteredOtp(): String{
        return listOf(otp1, otp2, otp3, otp4, otp5, otp6).joinToString("") { it.get().orEmpty() }
    }

    fun hitVerifySignupOtpApi(request: VerifySignupOtpRequest) {
        val mainRepository = MainRepository(RetrofitBuilder.apiService)
        viewModelScope.launch {
            getVerifySignupOtpResponse.postValue(Resource.loading(null))
            try {

                getVerifySignupOtpResponse.postValue(
                    Resource.success(
                        mainRepository.verifySignupOtpApi(request)

                    )
                )
            } catch (ex: IOException) {
                getVerifySignupOtpResponse.postValue(
                    Resource.error(
                        StatusCode.STATUS_CODE_INTERNET_VALIDATION,
                        null
                    )
                )
            } catch (exception: Exception) {
                getVerifySignupOtpResponse.postValue(
                    Resource.error(
                        StatusCode.SERVER_ERROR_MESSAGE,
                        null
                    )
                )
            }


        }

    }

    fun hitVerifyForgotPasswordOtpApi(request: VerifySignupOtpRequest) {
        val mainRepository = MainRepository(RetrofitBuilder.apiService)
        viewModelScope.launch {
            getVerifyForgotPasswordOtpResponse.postValue(Event(Resource.loading(null)))
            try {

                getVerifyForgotPasswordOtpResponse.postValue(Event(
                    Resource.success(
                        mainRepository.verifyForgotPasswordOtpApi(request)

                    )
                ))
            } catch (ex: IOException) {
                getVerifyForgotPasswordOtpResponse.postValue(Event(
                    Resource.error(
                        StatusCode.STATUS_CODE_INTERNET_VALIDATION,
                        null
                    )
                ))
            } catch (exception: Exception) {
                getVerifyForgotPasswordOtpResponse.postValue(Event(
                    Resource.error(
                        StatusCode.SERVER_ERROR_MESSAGE,
                        null
                    )
                ))
            }


        }

    }


    private fun validateOtp(): Boolean {
        val fields = listOf(otp1, otp2, otp3, otp4, otp5, otp6)

        if (fields.any { it.get().isNullOrEmpty() }) {
            _validationMessage.value = "Please enter all 6 digits of the OTP"
            return false
        }

        return true
    }
}
