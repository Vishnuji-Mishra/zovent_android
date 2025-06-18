package com.app.zovent.ui.main.view_model

import android.content.Intent
import android.util.Patterns
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.app.zovent.R
import com.app.zovent.ui.base.BaseViewModel
import com.app.zovent.ui.main.activity.DashboardActivity

class SigninViewModel: BaseViewModel() {
    val email = ObservableField<String>()
    val password = ObservableField<String>()

    private val _closeActivityEvent = MutableLiveData<Unit>()
    val closeActivityEvent: LiveData<Unit> = _closeActivityEvent

    private val _validationMessage = MutableLiveData<String>()
    val validationMessage: LiveData<String> = _validationMessage

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

        val context = view.context
        val intent = Intent(context, DashboardActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        context.startActivity(intent)
    // _validationMessage.value = "Successful!"
    }
}