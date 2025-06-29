package com.app.zovent.data.model.signin.response

data class LoginResponse(
    val response: Response
) {
    data class Response(
        val Result: ResultClass,
        val code: Int,
        val message: String
    )
}

data class ResultClass(val username: String?=null, val token: String?=null)