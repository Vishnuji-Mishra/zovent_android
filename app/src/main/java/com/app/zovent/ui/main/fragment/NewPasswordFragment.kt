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
import com.app.zovent.databinding.FragmentNewPasswordBinding
import com.app.zovent.databinding.FragmentOTPBinding
import com.app.zovent.databinding.FragmentOTPBinding.bind
import com.app.zovent.ui.base.BaseFragment
import com.app.zovent.ui.main.custom.GenericKeyEvent
import com.app.zovent.ui.main.custom.GenericTextWatcher
import com.app.zovent.ui.main.view_model.NewPasswordViewModel
import com.app.zovent.ui.main.view_model.OTPViewModel
import com.app.zovent.utils.CommonUtils
import com.app.zovent.utils.ProcessDialog
import com.app.zovent.utils.Status
import com.google.gson.Gson
import kotlin.getValue

class NewPasswordFragment : BaseFragment<FragmentNewPasswordBinding, NewPasswordViewModel>(R.layout.fragment_new_password) {
    override val binding: FragmentNewPasswordBinding by viewBinding(FragmentNewPasswordBinding::bind)
    override val mViewModel: NewPasswordViewModel by viewModels()
    private var isPassHidden = true
    private var isConfirmPassHidden = true
    private val args: NewPasswordFragmentArgs by navArgs()


    override fun isNetworkAvailable(boolean: Boolean) {}

    override fun setupViewModel() {
        mViewModel.getNewPasswordResponse.observe(viewLifecycleOwner){
            when (it.status) {
                Status.SUCCESS -> {
                    ProcessDialog.dismissDialog()
                    Log.i("TAG", "setupObservers: "+Gson().toJson(it.data))

                    findNavController().navigate(R.id.action_newPasswordFragment_to_signinFragment)
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

    }

    override fun setupViews() {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mViewModel
        }
        setPasswordToggle()
        setConfirmPasswordToggle()
        mViewModel.from = args.from
        mViewModel.email = args.email
        mViewModel.email = args.otp
    }

    override fun setupObservers() {
        mViewModel.validationMessage.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }
    private fun setPasswordToggle() {
        binding.newPassword.transformationMethod = CommonUtils.DotPasswordTransformationMethod
        binding.newPasswordToggle.setOnClickListener {
            isPassHidden = if (isPassHidden) {
                binding.newPasswordToggle.setImageResource(R.drawable.open_eye)
                binding.newPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                false
            } else {
                binding.newPasswordToggle.setImageResource(R.drawable.close_eye)
                binding.newPassword.transformationMethod = CommonUtils.DotPasswordTransformationMethod
                true
            }
            binding.newPassword.setSelection(binding.newPassword.text.toString().length)
        }
    }
    private fun setConfirmPasswordToggle() {
        binding.confirmPassword.transformationMethod = CommonUtils.DotPasswordTransformationMethod
        binding.confirmPasswordToggle.setOnClickListener {
            isConfirmPassHidden = if (isConfirmPassHidden) {
                binding.confirmPasswordToggle.setImageResource(R.drawable.open_eye)
                binding.confirmPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                false
            } else {
                binding.confirmPasswordToggle.setImageResource(R.drawable.close_eye)
                binding.confirmPassword.transformationMethod = CommonUtils.DotPasswordTransformationMethod
                true
            }
            binding.confirmPassword.setSelection(binding.confirmPassword.text.toString().length)
        }
    }

}