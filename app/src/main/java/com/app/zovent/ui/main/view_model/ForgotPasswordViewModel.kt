package com.app.zovent.ui.main.view_model

import android.util.Patterns
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.app.zovent.R
import com.app.zovent.ui.base.BaseViewModel
import com.app.zovent.ui.main.fragment.ForgotPasswordFragmentDirections
import kotlin.text.trim

class ForgotPasswordViewModel: BaseViewModel() {
    val email = ObservableField<String>()
    private val _validationMessage = MutableLiveData<String>()
    val validationMessage: LiveData<String> = _validationMessage

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
         _validationMessage.value = "Successful!"
        view.findNavController().navigate(ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToOTPFragment2(from = "forgot"))
    }
}