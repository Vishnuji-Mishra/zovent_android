package com.app.zovent.data.model.get_district.response

data class GetDistrictResponse(
    val response: Response
) {
    data class Response(
        val Result: ResultClass,
        val code: Int,
        val message: String
    ) {
        data class ResultClass(
            val district: String
        )
    }
}