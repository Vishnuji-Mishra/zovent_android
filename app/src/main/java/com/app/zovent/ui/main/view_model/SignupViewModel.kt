package com.app.zovent.ui.main.view_model

import android.net.Uri
import android.util.Patterns
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.app.zovent.R
import com.app.zovent.ui.base.BaseViewModel
import com.app.zovent.ui.main.fragment.SignupFragmentDirections

class SignupViewModel: BaseViewModel() {
    val email = ObservableField<String>()
    val username = ObservableField<String>()
    val password = ObservableField<String>()
    val mobile = ObservableField<String>()
    val businessName = ObservableField<String>()
    val gstRegistrationNumber = ObservableField<String>()
    val foodLicenceNumber = ObservableField<String>()
    val drugLicenceNumber = ObservableField<String>()
    val areaPinCode = ObservableField<String>()
    val districtName = ObservableField<String>()
    val showGstInput = ObservableBoolean(true)
    val showFoodInput = ObservableBoolean(true)
    val showDrugInput = ObservableBoolean(true)
    val isBusinessTypeRetail = ObservableBoolean(true)
    val isCompanyLogoPicked = ObservableBoolean(false)
    var companyLogoUri: Uri? = Uri.EMPTY

    private val _validationMessage = MutableLiveData<String>()
    val validationMessage: LiveData<String> = _validationMessage



    fun onClick(view: View) {
        when(view.id){
            R.id.signupButton->{
                onSubmit(view)
            }
            R.id.backButton->{
                view.findNavController().popBackStack()
            }
            R.id.yesGstRegistration->{
                showGstInput.set(true)
            }
            R.id.noGstRegistration->{
                showGstInput.set(false)
            }

            R.id.yesFoodLicnece->{
                showFoodInput.set(true)
            }
            R.id.noFoodLicence->{
                showFoodInput.set(false)
            }

            R.id.yesDrugLicnece->{
                showDrugInput.set(true)
            }
            R.id.noDrugLicence->{
                showDrugInput.set(false)
            }

            R.id.retail->{
                isBusinessTypeRetail.set(true)
            }
            R.id.distributor->{
                isBusinessTypeRetail.set(false)
            }
        }
    }

    fun onSubmit(view: View) {
        val emailInput = email.get()?.trim()
        val usernameInput = username.get()?.trim()
        val passwordInput = password.get()?.trim()
        val mobileInput = mobile.get()?.trim()
        val businessInput = businessName.get()?.trim()
        val gstInput = gstRegistrationNumber.get()?.trim()
        val foodInput = foodLicenceNumber.get()?.trim()
        val drugInput = drugLicenceNumber.get()?.trim()
        val areaInput = areaPinCode.get()?.trim()
        val districtInput = districtName.get()?.trim()

        if (usernameInput.isNullOrEmpty()) {
            _validationMessage.value = "Please enter username"
            return
        }

        if (emailInput.isNullOrEmpty()) {
            _validationMessage.value = "Please enter email"
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            _validationMessage.value = "Please enter a valid email"
            return
        }

        if (passwordInput.isNullOrEmpty()) {
            _validationMessage.value = "Please enter password"
            return
        }

        if (mobileInput.isNullOrEmpty()) {
            _validationMessage.value = "Please enter mobile number"
            return
        }

        if (businessInput.isNullOrEmpty()) {
            _validationMessage.value = "Please enter business name"
            return
        }

        if (showGstInput.get() && gstInput.isNullOrEmpty()) {
            _validationMessage.value = "Please enter GST registration number"
            return
        }

        if (showFoodInput.get() && foodInput.isNullOrEmpty()) {
            _validationMessage.value = "Please enter Food Licence number"
            return
        }

        if (showDrugInput.get() && isBusinessTypeRetail.get() && drugInput.isNullOrEmpty()) {
            _validationMessage.value = "Please enter Drug Licence number"
            return
        }

        if (areaInput.isNullOrEmpty()) {
            _validationMessage.value = "Please enter area pin code"
            return
        }

        if (districtInput.isNullOrEmpty()) {
            _validationMessage.value = "Please enter district name"
            return
        }

        // All validations passed — hit API or navigate
        _validationMessage.value = "Successful!"
        view.findNavController().navigate(SignupFragmentDirections.actionSignupFragmentToOTPFragment("signup"))
    }
}