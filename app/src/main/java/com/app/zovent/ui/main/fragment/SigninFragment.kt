package com.app.zovent.ui.main.fragment

import android.content.Intent
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
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.zovent.R
import com.app.zovent.databinding.FragmentSigninBinding
import com.app.zovent.ui.base.BaseFragment
import com.app.zovent.ui.main.activity.DashboardActivity
import com.app.zovent.ui.main.view_model.SigninViewModel
import com.app.zovent.utils.CommonUtils
import com.app.zovent.utils.PreferenceEntity.IS_LOGIN
import com.app.zovent.utils.PreferenceEntity.TOKEN
import com.app.zovent.utils.Preferences
import com.app.zovent.utils.ProcessDialog
import com.app.zovent.utils.Status
import com.app.zovent.utils.StatusCode
import com.google.gson.Gson
import kotlin.toString

class SigninFragment : BaseFragment<FragmentSigninBinding, SigninViewModel>(R.layout.fragment_signin) {
    override val binding: FragmentSigninBinding by viewBinding(FragmentSigninBinding::bind)
    override val mViewModel: SigninViewModel by viewModels()
    private var isPassHidden = true

    override fun isNetworkAvailable(boolean: Boolean) {}

    override fun setupViewModel() {}

    override fun setupViews() {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner

            viewModel = mViewModel
        }
        setWelcomeText(binding.welcomeText)
        registerNowText()
        setPasswordToggle()
    }

    override fun setupObservers() {
        mViewModel.validationMessage.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
        mViewModel.closeActivityEvent.observe(viewLifecycleOwner) {
            requireActivity().finish()
        }
        mViewModel.loginResponse.observe(viewLifecycleOwner){
            when (it.status) {
                Status.SUCCESS -> {
                    ProcessDialog.dismissDialog()
                    Log.i("TAG", "setupObservers: "+Gson().toJson(it.data))
                    Toast.makeText(requireContext(), it.data?.response?.message, Toast.LENGTH_SHORT).show()
                    if (it.data?.response?.code==200){
                        Preferences.setStringPreference(requireContext(), IS_LOGIN, "2")
                        Preferences.setStringPreference(requireContext(), TOKEN, it.data.response.Result.token ?: "")
                        CommonUtils.hideKeyboard(requireActivity())
                        val intent = Intent(requireContext(), DashboardActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }
                        requireContext().startActivity(intent)
                    }

                }
                Status.LOADING -> {

                    ProcessDialog.startDialog(requireContext())


                }
                Status.ERROR -> {
                    ProcessDialog.dismissDialog()

                    it.message?.let {
//                        showSnackBar(it)

                    }
                }

            }
        }
    }

    fun setWelcomeText(textView: TextView) {
        val fullText = getString(R.string.welcome_back_to_zovent)
        val spannable = SpannableString(fullText)

        val targetWord = getString(R.string.zovent)
        val startIndex = fullText.indexOf(targetWord)

        val colors = listOf(
            "#1E90FF", // Z - Red
            "#1E90FF", // o - Orange
            "#FF69B4", // v - Yellow
            "#FFA500", // e - Green
            "#1E90FF", // n - Blue
            "#FFA500"  // t - Purple
        )

        for (i in targetWord.indices) {
            spannable.setSpan(
                ForegroundColorSpan(Color.parseColor(colors[i])),
                startIndex + i,
                startIndex + i + 1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        textView.text = spannable
    }
    fun registerNowText(){

        val fullText = getString(R.string.haven_t_account_register_now)
        val spannable = SpannableString(fullText)

        val part1 = getString(R.string.haven_t_account)
        val part2 = getString(R.string.register_now)

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
                findNavController().navigate(R.id.action_signinFragment_to_signupFragment)
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
    private fun setPasswordToggle() {
        binding.password.transformationMethod = CommonUtils.DotPasswordTransformationMethod
        binding.passwordToggle.setOnClickListener {
            isPassHidden = if (isPassHidden) {
                binding.passwordToggle.setImageResource(R.drawable.open_eye)
                binding.password.transformationMethod = HideReturnsTransformationMethod.getInstance()
                false
            } else {
                binding.passwordToggle.setImageResource(R.drawable.close_eye)
                binding.password.transformationMethod = CommonUtils.DotPasswordTransformationMethod
                true
            }
            binding.password.setSelection(binding.password.text.toString().length)
        }
    }


}