package com.app.zovent.ui.main.view_model

import android.net.Uri
import android.util.Patterns
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.app.zovent.R
import com.app.zovent.data.api.RetrofitBuilder
import com.app.zovent.data.model.forgot_password.request.ForgotPasswordRequest
import com.app.zovent.data.model.get_district.response.GetDistrictResponse
import com.app.zovent.data.model.signin.response.LoginResponse
import com.app.zovent.data.model.signup.request.Profile
import com.app.zovent.data.model.signup.request.SignupRequest
import com.app.zovent.data.model.signup.response.SignupResponse
import com.app.zovent.data.repository.MainRepository
import com.app.zovent.ui.base.BaseViewModel
import com.app.zovent.ui.main.fragment.SignupFragmentDirections
import com.app.zovent.utils.Resource
import com.app.zovent.utils.StatusCode
import kotlinx.coroutines.launch
import java.io.IOException

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

    var getDistrictNameResponse = MutableLiveData<Resource<GetDistrictResponse>>()
    var getSignupResponse = MutableLiveData<Resource<SignupResponse>>()

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
//        _validationMessage.value = "Successful!"
        /*val request = SignupRequest(
            username = usernameInput,
            email = emailInput,
            password = passwordInput,
            mobile_number = mobileInput,
            profile = Profile(
                business_name = businessInput,
                business_type = if (isBusinessTypeRetail.get()) "Retailer" else "Distributer",
                gst_registered = if (showGstInput.get()) "Yes" else "No",
                gst_number = if (showGstInput.get()) gstInput else null,
                food_license = if (showFoodInput.get()) "Yes" else "No",
                food_liscense_number = if (showFoodInput.get()) foodInput else null,
                drug_license = if (showDrugInput.get()) "Yes" else "No",
                drug_liscense_number = if (showDrugInput.get()) drugInput else null,

            ))*/
        hitSignupApi(
            username = usernameInput,
            email = emailInput,
            password = passwordInput,
            mobileNumber = mobileInput,
            businessName = businessInput,
            businessType = if (isBusinessTypeRetail.get()) "Retailer" else "Distributer",
            gstRegistered = if (showGstInput.get()) "Yes" else "No",
            gstNumber = if (showGstInput.get()) gstInput else null,
            foodLicense = if (showFoodInput.get()) "Yes" else "No",
            foodLicenseNumber = if (showFoodInput.get()) foodInput else null,
            drugLicense = if (showDrugInput.get()) "Yes" else "No",
            drugLicenseNumber = if (showDrugInput.get()) drugInput else null,
            areaPincode = areaInput ?: "",
            districtName = districtInput ?: ""
        )
    }

    fun hitGetDistrictNameApi(pincode: String) {
        val mainRepository = MainRepository(RetrofitBuilder.apiService)
        viewModelScope.launch {
            getDistrictNameResponse.postValue(Resource.loading(null))
            try {

                getDistrictNameResponse.postValue(
                    Resource.success(
                        mainRepository.getDistrictNameApi(pincode)

                    )
                )
            } catch (ex: IOException) {
                getDistrictNameResponse.postValue(
                    Resource.error(
                        StatusCode.STATUS_CODE_INTERNET_VALIDATION,
                        null
                    )
                )
            } catch (exception: Exception) {
                getDistrictNameResponse.postValue(
                    Resource.error(
                        StatusCode.SERVER_ERROR_MESSAGE,
                        null
                    )
                )
            }


        }

    }

    fun hitSignupApi(
        username: String,
        email: String,
        password: String,
        mobileNumber: String,
        businessName: String,
        businessType: String,
        gstRegistered: String,
        gstNumber: String?,
        foodLicense: String,
        foodLicenseNumber: String?,
        drugLicense: String,
        drugLicenseNumber: String?,
        areaPincode: String,
        districtName: String
    ) {
        val mainRepository = MainRepository(RetrofitBuilder.apiService)
        viewModelScope.launch {
            getSignupResponse.postValue(Resource.loading(null))
            try {
                val response = mainRepository.signupApi(
                    username,
                    email,
                    password,
                    mobileNumber,
                    businessName,
                    businessType,
                    gstRegistered,
                    gstNumber,
                    foodLicense,
                    foodLicenseNumber,
                    drugLicense,
                    drugLicenseNumber,
                    areaPincode,
                    districtName
                )
                getSignupResponse.postValue(Resource.success(response))
            } catch (ex: IOException) {
                getSignupResponse.postValue(Resource.error(StatusCode.STATUS_CODE_INTERNET_VALIDATION, null))
            } catch (exception: Exception) {
                getSignupResponse.postValue(Resource.error(StatusCode.SERVER_ERROR_MESSAGE, null))
            }
        }
    }


}