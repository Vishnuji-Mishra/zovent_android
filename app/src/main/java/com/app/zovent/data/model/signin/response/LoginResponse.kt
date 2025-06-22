package com.app.zovent.data.model.signin.response

data class LoginResponse(
    val error: Boolean,
    val message: String,
    val username: String,
    val token: String,

)