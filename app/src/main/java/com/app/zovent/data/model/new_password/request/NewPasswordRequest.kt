package com.app.zovent.data.model.new_password.request

data class NewPasswordRequest(val otp: String? = null, val email: String? = null, val new_password: String? = null)
