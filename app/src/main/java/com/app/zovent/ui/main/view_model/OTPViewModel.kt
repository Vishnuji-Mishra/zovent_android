package com.app.zovent.ui.main.view_model

import android.content.Intent
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.app.zovent.R
import com.app.zovent.ui.base.BaseViewModel
import com.app.zovent.ui.main.activity.DashboardActivity

class OTPViewModel : BaseViewModel() {

    val otp1 = ObservableField<String>()
    val otp2 = ObservableField<String>()
    val otp3 = ObservableField<String>()
    val otp4 = ObservableField<String>()

    var from = ""

    private val _validationMessage = MutableLiveData<String>()
    val validationMessage: LiveData<String> get() = _validationMessage

    fun onClick(view: View) {
        when (view.id) {
            R.id.backButton -> {
                view.findNavController().popBackStack()
            }
            R.id.verifyButton -> {
                if (!validateOtp()) return

                when (from) {
                    "signup" -> {
                        val context = view.context
                        val intent = Intent(context, DashboardActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }
                        context.startActivity(intent)
                    }
                    "forgot" -> {
                        view.findNavController().navigate(R.id.action_OTPFragment_to_newPasswordFragment)
                    }
                }
            }
        }
    }

    private fun validateOtp(): Boolean {
        val o1 = otp1.get()?.trim()
        val o2 = otp2.get()?.trim()
        val o3 = otp3.get()?.trim()
        val o4 = otp4.get()?.trim()

        if (o1.isNullOrEmpty() || o2.isNullOrEmpty() || o3.isNullOrEmpty() || o4.isNullOrEmpty()) {
            _validationMessage.value = "Please enter all 4 digits of the OTP"
            return false
        }

        // You can also concatenate and validate the full OTP if needed
        val enteredOtp = o1 + o2 + o3 + o4

        // If you want to compare against a correct OTP, do it here
        // Example: if (enteredOtp != expectedOtp) { _validationMessage.value = "Incorrect OTP"; return false }

        return true
    }
}
