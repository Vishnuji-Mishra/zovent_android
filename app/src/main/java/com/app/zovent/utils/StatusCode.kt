package com.app.zovent.utils

object StatusCode {
    const val STATUS_CODE_INTERNET_VALIDATION = "No internet connection\nPlease connect to wifi or mobile data"

    const val STATUS_CODE_PASSWORD_CHANGED= 101
    const val STATUS_CODE_USER_SINGLE_LOGIN = 403
    const val STATUS_CODE_USER_BLOCKED = 201
    //const val STATUS_CODE_USER_BLOCKED = 401
    const val STATUS_CODE_USER_NOT_VERIFIED = 423

    const val STATUS_CODE_SUCCESS = 200
    const val STATUS_CODE_SERVER_ERROR = 500

    const val SERVER_ERROR_MESSAGE = "Sorry! Something is wrong, Please try again."

}