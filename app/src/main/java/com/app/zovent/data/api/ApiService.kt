package com.app.zovent.data.api

import com.app.zovent.data.model.forgot_password.request.ForgotPasswordRequest
import com.app.zovent.data.model.forgot_password.response.ForgotPasswordResponse
import com.app.zovent.data.model.get_district.response.GetDistrictResponse
import com.app.zovent.data.model.new_password.request.NewPasswordRequest
import com.app.zovent.data.model.signin.request.LoginRequest
import com.app.zovent.data.model.signin.response.LoginResponse
import com.app.zovent.data.model.signup.request.SignupRequest
import com.app.zovent.data.model.signup.response.SignupResponse
import com.app.zovent.data.model.verify_signup_otp.request.VerifySignupOtpRequest
import com.app.zovent.data.model.verify_signup_otp.response.VerifySignupOtpResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiService {
    @POST("login/")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("pincode-lookup/")
    suspend fun districtName(@Query("pincode") pincode: String? = null): GetDistrictResponse

    @FormUrlEncoded
    @POST("register/")
    suspend fun signup(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("mobile_number") mobileNumber: String,
        @Field("profile[business_name]") businessName: String,
        @Field("profile[business_type]") businessType: String,
        @Field("profile[gst_registered]") gstRegistered: String,
        @Field("profile[gst_number]") gstNumber: String?,
        @Field("profile[food_license]") foodLicense: String,
        @Field("profile[food_liscense_number]") foodLicenseNumber: String?,
        @Field("profile[drug_license]") drugLicense: String,
        @Field("profile[drug_liscense_number]") drugLicenseNumber: String?,
        @Field("profile[area_pincode]") areaPincode: String,
        @Field("profile[district_name]") districtName: String
    ): SignupResponse

    @POST("verify-signup-otp/")
    suspend fun verifySignupOtp(@Body request: VerifySignupOtpRequest): VerifySignupOtpResponse



    @POST("forgot-password/")
    suspend fun forgotPassword(@Body request: ForgotPasswordRequest): ForgotPasswordResponse

    @POST("verify-reset-otp/")
    suspend fun verifyForgotPasswordOtp(@Body request: VerifySignupOtpRequest): VerifySignupOtpResponse

    @POST("set-new-password/ ")
    suspend fun createNewPassword(@Body request: NewPasswordRequest): VerifySignupOtpResponse

}