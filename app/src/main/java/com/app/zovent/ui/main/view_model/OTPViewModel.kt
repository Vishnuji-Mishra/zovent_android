package com.app.zovent.ui.main.view_model

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.app.zovent.R
import com.app.zovent.ui.base.BaseViewModel

class OTPViewModel: BaseViewModel() {
    fun onClick(view: View) {
        when(view.id){
            R.id.backButton->{
                view.findNavController().popBackStack()
            }
        }
    }
}