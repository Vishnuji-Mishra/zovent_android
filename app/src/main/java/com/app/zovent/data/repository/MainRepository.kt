package com.app.zovent.data.repository

import com.app.zovent.data.api.ApiService
import com.app.zovent.data.model.forgot_password.request.ForgotPasswordRequest
import com.app.zovent.data.model.new_password.request.NewPasswordRequest
import com.app.zovent.data.model.signin.request.LoginRequest
import com.app.zovent.data.model.signup.request.SignupRequest
import com.app.zovent.data.model.signup.response.SignupResponse
import com.app.zovent.data.model.verify_signup_otp.request.VerifySignupOtpRequest
import com.app.zovent.data.model.verify_signup_otp.response.VerifySignupOtpResponse

class MainRepository(private val apiService: ApiService) {
    suspend fun loginApi(request: LoginRequest) = apiService.login(request)
    suspend fun signupApi(
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
    ): SignupResponse {
        return apiService.signup(
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
    }
    suspend fun verifySignupOtpApi(request: VerifySignupOtpRequest) = apiService.verifySignupOtp(request)
    suspend fun getDistrictNameApi(pincode: String) = apiService.districtName(pincode)


    suspend fun forgotPasswordApi(request: ForgotPasswordRequest) = apiService.forgotPassword(request)
    suspend fun verifyForgotPasswordOtpApi(request: VerifySignupOtpRequest) = apiService.verifyForgotPasswordOtp(request)
    suspend fun newPasswordApi(request: NewPasswordRequest) = apiService.createNewPassword(request)

}