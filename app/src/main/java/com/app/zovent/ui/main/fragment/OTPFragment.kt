package com.app.zovent.ui.main.fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.zovent.R
import com.app.zovent.databinding.FragmentOTPBinding
import com.app.zovent.databinding.FragmentSigninBinding
import com.app.zovent.ui.base.BaseFragment
import com.app.zovent.ui.main.activity.DashboardActivity
import com.app.zovent.ui.main.custom.GenericKeyEvent
import com.app.zovent.ui.main.custom.GenericTextWatcher
import com.app.zovent.ui.main.view_model.OTPViewModel
import com.app.zovent.ui.main.view_model.SigninViewModel
import com.app.zovent.utils.CommonUtils
import com.app.zovent.utils.PreferenceEntity.IS_LOGIN
import com.app.zovent.utils.PreferenceEntity.TOKEN
import com.app.zovent.utils.Preferences
import com.app.zovent.utils.ProcessDialog
import com.app.zovent.utils.Status
import com.google.gson.Gson

class OTPFragment : BaseFragment<FragmentOTPBinding, OTPViewModel>(R.layout.fragment_o_t_p) {
    override val binding: FragmentOTPBinding by viewBinding(FragmentOTPBinding::bind)
    override val mViewModel: OTPViewModel by viewModels()
    private val args: OTPFragmentArgs by navArgs()

    override fun isNetworkAvailable(boolean: Boolean) {}

    override fun setupViewModel() {

    }

    override fun setupViews() {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mViewModel
        }
        resendNowText()
        customOtp()
//        Log.i("TAG", "setupViews: "+args.from)
        mViewModel.from = args.from
        mViewModel.email = args.email
    }

    override fun setupObservers() {
        mViewModel.validationMessage.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
        mViewModel.getVerifySignupOtpResponse.observe(viewLifecycleOwner){
            when (it.status) {
                Status.SUCCESS -> {
                    ProcessDialog.dismissDialog()
                    Log.i("TAG", "setupObservers: "+Gson().toJson(it.data))
                    Preferences.setStringPreference(requireContext(), IS_LOGIN, "2")
                    Preferences.setStringPreference(requireContext(), TOKEN, it.data?.token ?: "")
                    CommonUtils.hideKeyboard(requireActivity())
                    val intent = Intent(requireContext(), DashboardActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    requireContext().startActivity(intent)
                }
                Status.LOADING -> {

                    ProcessDialog.startDialog(requireContext())


                }
                Status.ERROR -> {
                    ProcessDialog.dismissDialog()

                    it.message?.let {
//                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }

        mViewModel.getVerifyForgotPasswordOtpResponse.observe(viewLifecycleOwner){ event ->
            event.getContentIfNotHandled()?.let { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    ProcessDialog.dismissDialog()
                    Log.i("TAG", "setupObservers: " + Gson().toJson(result.data))
                    Toast.makeText(requireContext(), result.data?.message, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(
                        OTPFragmentDirections.actionOTPFragmentToNewPasswordFragment(
                            from = mViewModel.from,
                            email = mViewModel.email,
                            otp = mViewModel.getEnteredOtp()
                        )
                    )

                }

                Status.LOADING -> {

                    ProcessDialog.startDialog(requireContext())


                }

                Status.ERROR -> {
                    ProcessDialog.dismissDialog()

                    result.message?.let {
//                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            }
        }


    }
    private fun customOtp() {
        val etOtp1 = binding.etOtp1
        val etOtp2 = binding.etOtp2
        val etOtp3 = binding.etOtp3
        val etOtp4 = binding.etOtp4
        val etOtp5 = binding.etOtp5
        val etOtp6 = binding.etOtp6

        etOtp1.addTextChangedListener(GenericTextWatcher(etOtp1, etOtp2))
        etOtp2.addTextChangedListener(GenericTextWatcher(etOtp2, etOtp3))
        etOtp3.addTextChangedListener(GenericTextWatcher(etOtp3, etOtp4))
        etOtp4.addTextChangedListener(GenericTextWatcher(etOtp4, etOtp5))
        etOtp5.addTextChangedListener(GenericTextWatcher(etOtp5, etOtp6))
        etOtp6.addTextChangedListener(GenericTextWatcher(etOtp6, null))

        etOtp1.setOnKeyListener(GenericKeyEvent(etOtp1, null))
        etOtp2.setOnKeyListener(GenericKeyEvent(etOtp2, etOtp1))
        etOtp3.setOnKeyListener(GenericKeyEvent(etOtp3, etOtp2))
        etOtp4.setOnKeyListener(GenericKeyEvent(etOtp4, etOtp3))
        etOtp5.setOnKeyListener(GenericKeyEvent(etOtp5, etOtp4))
        etOtp6.setOnKeyListener(GenericKeyEvent(etOtp6, etOtp5))
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