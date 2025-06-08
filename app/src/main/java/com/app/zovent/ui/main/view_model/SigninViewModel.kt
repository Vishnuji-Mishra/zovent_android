package com.app.zovent.ui.main.view_model

import android.util.Patterns
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.app.zovent.R
import com.app.zovent.ui.base.BaseViewModel

class SigninViewModel: BaseViewModel() {
    val email = ObservableField<String>()
    val password = ObservableField<String>()

    private val _validationMessage = MutableLiveData<String>()
    val validationMessage: LiveData<String> = _validationMessage

    fun onClick(view: View) {
        when(view.id){
            R.id.loginButton->{
                onSubmit(view)
            }
            R.id.backButton->{
                view.findNavController().popBackStack()
            }
        }
    }
    fun onSubmit(view: View) {
        val emailInput = email.get()?.trim()
        val passwordInput = password.get()?.trim()

        if (emailInput.isNullOrEmpty()) {
            _validationMessage.value = "Please Enter Email"
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            _validationMessage.value = "Please Enter Valid Email"
            return
        }

        if (passwordInput.isNullOrEmpty()) {
            _validationMessage.value = "Please Enter Password"
            return
        }



        // Success hit API here
        view.findNavController().navigate(R.id.action_signinFragment_to_OTPFragment)
//        _validationMessage.value = "Successful!"
    }

}