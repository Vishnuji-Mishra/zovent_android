package com.app.zovent.data.model.forgot_password.response

data class ForgotPasswordResponse(
    val response: Response
) {
    data class Response(
        val Result: Any,
        val code: Int,
        val message: String
    )
}