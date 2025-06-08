package com.app.zovent.utils

import android.util.Log
import com.google.android.datatransport.BuildConfig
import com.google.gson.Gson
//import androidx.databinding.ktx.BuildConfig

object LogUtil {

    private const val LOG_PREFIX = "LOG_"
    private const val MAX_LOG_SIZE = 5000
    private var LOG_ENABLED = BuildConfig.DEBUG

    /**
     * Helper method to write a String value to [Log].
     *
     * @param tag String: Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param message String: The message you would like logged.
     */
    fun w(tag: String, message: String) {
        if (LOG_ENABLED) Log.w(tag.prependIndent(LOG_PREFIX), message)
    }

    /**
     * Helper method to write a String value to [Log].
     *
     * @param tag String: Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param message String: The message you would like logged.
     */
    fun d(tag: String, message: String) {
        if (LOG_ENABLED) Log.d(tag.prependIndent(LOG_PREFIX), message)
    }

    /**
     * Helper method to write a String value to [Log].
     *
     * @param tag String: Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param message String: The message you would like logged.
     */
    fun e(tag: String, message: String) {
        if (LOG_ENABLED) {
            for (i in 0..message.length / MAX_LOG_SIZE) {
                val start = i * MAX_LOG_SIZE
                var end = (i + 1) * MAX_LOG_SIZE
                end = if (end > message.length) message.length else end
                Log.e(tag.prependIndent(LOG_PREFIX), message.substring(start, end))
            }
        }
    }

    /**
     * Helper method to write a String value to [Log].
     *
     * @param tag String: Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param message String: The message you would like logged.
     * @param e Throwable: An exception to log
     */
    fun e(tag: String, message: String, e: Throwable?) {
        if (LOG_ENABLED) Log.e(LOG_PREFIX + tag, message, e)
    }

    /**
     * Helper method to write a String value to [Log].
     *
     * @param tag String: Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param message String: The message you would like logged.
     */
    fun i(tag: String, message: String) {
        if (LOG_ENABLED) Log.i(tag.prependIndent(LOG_PREFIX), message)
    }

    /**
     * Helper method to write a String value to [Log].
     *
     * @param tag String: Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param message String: The message you would like logged.
     */
    fun v(tag: String, message: String) {
        if (LOG_ENABLED) Log.v(tag.prependIndent(LOG_PREFIX), message)
    }

    fun objectLog(tag: String, obj: Any) {
        if (LOG_ENABLED) Log.e(tag.prependIndent(LOG_PREFIX), Gson().toJson(obj))

    }
}