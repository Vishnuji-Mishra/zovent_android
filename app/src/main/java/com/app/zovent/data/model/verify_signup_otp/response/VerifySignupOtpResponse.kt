package com.app.zovent.data.model.verify_signup_otp.response

data class VerifySignupOtpResponse(
    val response: Response
) {
    data class Response(
        val Result: ResultClass,
        val code: Int,
        val message: String
    ) {
        data class ResultClass(
            val email: String,
            val token: String,
            val profile: Profile
        ) {
            data class Profile(
                val area_pincode: String,
                val business_name: String,
                val business_type: String,
                val company_logo: Any,
                val district_name: String,
                val drug_license: String,
                val drug_license_number: Any,
                val food_license: String,
                val food_license_number: Any,
                val gst_number: Any,
                val gst_registered: String
            )
        }
    }
}