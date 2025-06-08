package com.app.zovent.ui.main.fragment

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.zovent.R
import com.app.zovent.databinding.FragmentOTPBinding
import com.app.zovent.databinding.FragmentSigninBinding
import com.app.zovent.ui.base.BaseFragment
import com.app.zovent.ui.main.custom.GenericKeyEvent
import com.app.zovent.ui.main.custom.GenericTextWatcher
import com.app.zovent.ui.main.view_model.OTPViewModel
import com.app.zovent.ui.main.view_model.SigninViewModel

class OTPFragment : BaseFragment<FragmentOTPBinding, OTPViewModel>(R.layout.fragment_o_t_p) {
    override val binding: FragmentOTPBinding by viewBinding(FragmentOTPBinding::bind)
    override val mViewModel: OTPViewModel by viewModels()

    override fun isNetworkAvailable(boolean: Boolean) {}

    override fun setupViewModel() {}

    override fun setupViews() {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mViewModel
        }
        resendNowText()
        customOtp()
    }

    override fun setupObservers() {}
    private fun customOtp() {
        val etOtp1 = binding.etOtp1
        val etOtp2 = binding.etOtp2
        val etOtp3 = binding.etOtp3
        val etOtp4 = binding.etOtp4
        etOtp1.addTextChangedListener(GenericTextWatcher(etOtp1,etOtp2))
        etOtp2.addTextChangedListener(GenericTextWatcher(etOtp2,etOtp3))
        etOtp3.addTextChangedListener(GenericTextWatcher(etOtp3,etOtp4))
        etOtp4.addTextChangedListener(GenericTextWatcher(etOtp4,null))

        etOtp1.setOnKeyListener(GenericKeyEvent(etOtp1,null))
        etOtp2.setOnKeyListener(GenericKeyEvent(etOtp2,etOtp1))
        etOtp3.setOnKeyListener(GenericKeyEvent(etOtp3,etOtp2))
        etOtp4.setOnKeyListener(GenericKeyEvent(etOtp4,etOtp3))
    }
    fun resendNowText(){

        val fullText = getString(R.string.didn_t_receive_code_resend_again)
        val spannable = SpannableString(fullText)

        val part1 = getString(R.string.didn_t_receive_code)
        val part2 = getString(R.string.resend_again)

// Color for "Haven’t account?"
        spannable.setSpan(
            ForegroundColorSpan(requireContext().getColor(R.color.black_323434)),  // change to your desired color
            0,
            part1.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

// Color and click for "Register now"
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                // Handle click here
                Toast.makeText(widget.context, "Resend clicked!", Toast.LENGTH_SHORT).show()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false  // remove underline
                ds.color = requireContext().getColor(R.color.blue_3B82F6)       // change to your desired color
            }
        }

        spannable.setSpan(
            clickableSpan,
            part1.length + 1,  // +1 for the space
            fullText.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.signup.text = spannable
        binding.signup.movementMethod = LinkMovementMethod.getInstance()
        binding.signup.highlightColor = Color.TRANSPARENT

    }


}