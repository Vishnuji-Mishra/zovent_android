package com.app.zovent.ui.main.custom

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.app.zovent.R

class GenericTextWatcher internal constructor(
    private val currentView: View,
    private val nextView: View?
) : TextWatcher {

    override fun afterTextChanged(editable: Editable) {
        val text = editable.toString()
        if (text.length == 1) {
            when (currentView.id) {
                R.id.etOtp1,
                R.id.etOtp2,
                R.id.etOtp3,
                R.id.etOtp4,
                R.id.etOtp5 -> nextView?.requestFocus()
            }
        }
    }

    override fun beforeTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {}
    override fun onTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {}
}
