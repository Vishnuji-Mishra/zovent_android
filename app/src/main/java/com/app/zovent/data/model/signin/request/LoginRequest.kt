package com.app.zovent.data.model.signin.request

data class LoginRequest(
    val username: String ?= null,
    val password: String ?= null
)