package com.app.zovent.data.model.common.response

data class CommonResponse(
    val response: Response
) {
    data class Response(
        val Result: Any,
        val code: Int,
        val message: String
    )
}