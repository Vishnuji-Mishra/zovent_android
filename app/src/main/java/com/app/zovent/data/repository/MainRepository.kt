package com.app.zovent.data.repository

import com.app.zovent.data.api.ApiService
import com.app.zovent.data.model.forgot_password.request.ForgotPasswordRequest
import com.app.zovent.data.model.new_password.request.NewPasswordRequest
import com.app.zovent.data.model.resend_otp.ResendOtpRequest
import com.app.zovent.data.model.signin.request.LoginRequest
import com.app.zovent.data.model.signup.request.SignupRequest
import com.app.zovent.data.model.signup.response.SignupResponse
import com.app.zovent.data.model.verify_signup_otp.request.VerifySignupOtpRequest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody

class MainRepository(private val apiService: ApiService) {
    suspend fun loginApi(request: LoginRequest) = apiService.login(request)
    /*suspend fun signupApi(
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
    }*/
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
        districtName: String,
        companyLogo: MultipartBody.Part? // <-- new param
    ): SignupResponse {
        return apiService.signup(
            username.toRequestBody(),
            email.toRequestBody(),
            password.toRequestBody(),
            mobileNumber.toRequestBody(),
            businessName.toRequestBody(),
            businessType.toRequestBody(),
            gstRegistered.toRequestBody(),
            gstNumber?.toRequestBody(),
            foodLicense.toRequestBody(),
            foodLicenseNumber?.toRequestBody(),
            drugLicense.toRequestBody(),
            drugLicenseNumber?.toRequestBody(),
            areaPincode.toRequestBody(),
            districtName.toRequestBody(),
            companyLogo
        )
    }

    suspend fun verifySignupOtpApi(request: VerifySignupOtpRequest) = apiService.verifySignupOtp(request)
    suspend fun getDistrictNameApi(pincode: String) = apiService.districtName(pincode)


    suspend fun forgotPasswordApi(request: ForgotPasswordRequest) = apiService.forgotPassword(request)
    suspend fun verifyForgotPasswordOtpApi(request: VerifySignupOtpRequest) = apiService.verifyForgotPasswordOtp(request)
    suspend fun newPasswordApi(request: NewPasswordRequest) = apiService.createNewPassword(request)
    suspend fun resendResetOtpApi(request: ResendOtpRequest) = apiService.resendResetOtp(request)
    suspend fun resendSignupOtpApi(request: ResendOtpRequest) = apiService.resendSignupOtp(request)


    fun String.toRequestBody(): RequestBody =
        RequestBody.create("text/plain".toMediaTypeOrNull(), this)

}