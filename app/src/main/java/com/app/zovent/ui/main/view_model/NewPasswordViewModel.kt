package com.app.zovent.ui.main.view_model

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.app.zovent.R
import com.app.zovent.ui.base.BaseViewModel

class NewPasswordViewModel : BaseViewModel() {
    val newPassword = ObservableField<String>()
    val confirmPassword = ObservableField<String>()

    private val _validationMessage = MutableLiveData<String>()
    val validationMessage: LiveData<String> = _validationMessage

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
        view.findNavController().navigate(R.id.action_newPasswordFragment_to_signinFragment)
        // _validationMessage.value = "Password successfully updated!"
    }
}
