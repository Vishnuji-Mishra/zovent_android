package com.app.zovent.ui.main.fragment

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.HideReturnsTransformationMethod
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.zovent.R
import com.app.zovent.databinding.FragmentSigninBinding
import com.app.zovent.databinding.FragmentSigninBinding.bind
import com.app.zovent.databinding.FragmentSignupBinding
import com.app.zovent.ui.base.BaseFragment
import com.app.zovent.ui.main.view_model.SigninViewModel
import com.app.zovent.ui.main.view_model.SignupViewModel
import com.app.zovent.utils.CommonUtils
import kotlin.getValue

class SignupFragment : BaseFragment<FragmentSignupBinding, SignupViewModel>(R.layout.fragment_signup) {
    override val binding: FragmentSignupBinding by viewBinding(FragmentSignupBinding::bind)
    override val mViewModel: SignupViewModel by viewModels()

    override fun isNetworkAvailable(boolean: Boolean) {}

    override fun setupViewModel() {}

    override fun setupViews() {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner

//            viewModel = mViewModel
        }
    }

    override fun setupObservers() {
    }




}