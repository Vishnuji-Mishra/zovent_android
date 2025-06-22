package com.app.zovent.data.model.signup.request

data class SignupRequest(
    val username: String? = null,
    val email: String? = null,
    val password: String? = null,
    val mobile_number: String? = null,
    val profile: Profile? = null
)

data class Profile(
    val business_name: String? = null,
    val business_type: String? = null,
    val gst_registered: String? = null,
    val gst_number: String? = null,
    val drug_liscense_number: String? = null,
    val food_liscense_number: String? = null,
    val drug_license: String? = null,
    val food_license: String? = null,
    val area_pincode: String? = null,
    val district_name: String? = null
)
