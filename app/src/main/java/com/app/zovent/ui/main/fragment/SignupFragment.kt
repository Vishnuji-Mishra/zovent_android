package com.app.zovent.ui.main.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.HideReturnsTransformationMethod
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.zovent.R
import com.app.zovent.databinding.FragmentSignupBinding
import com.app.zovent.ui.base.BaseFragment
import com.app.zovent.ui.main.view_model.SignupViewModel
import com.app.zovent.utils.CommonUtils
import kotlin.getValue

class SignupFragment : BaseFragment<FragmentSignupBinding, SignupViewModel>(R.layout.fragment_signup) {
    override val binding: FragmentSignupBinding by viewBinding(FragmentSignupBinding::bind)
    override val mViewModel: SignupViewModel by viewModels()
    private var isPassHidden = true

    override fun isNetworkAvailable(boolean: Boolean) {}

    override fun setupViewModel() {}

    override fun setupViews() {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mViewModel
            binding.clickEvent = ::clickEvent
        }
        setWelcomeText(binding.welcomeText)
        setPasswordToggle()
        alreadyHaveAccountText()
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

    fun clickEvent(type: Int) {
        when (type) {
            0->{
                checkGalleryPermissionAndPick()
            }
            1->{
                onRemoveCompanyLogo()
            }
        }
    }
    fun onRemoveCompanyLogo(){
        binding.chooseFile.text = getString(R.string.choose_file)
        binding.chooseFile.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.choose_file), null, null, null)
        binding.removeCompanyLogo.isVisible = false
        mViewModel.isCompanyLogoPicked.set(false)
        mViewModel.companyLogoUri = Uri.EMPTY
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

    override fun setupObservers() {
        mViewModel.validationMessage.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    fun alreadyHaveAccountText(){

        val fullText = "Already have an account? Login"
        val spannable = SpannableString(fullText)

        val part1 = "Already have an account?"
        val part2 = " Login"

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
                findNavController().navigate(R.id.action_signupFragment_to_signinFragment)
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

        binding.goToLogin.text = spannable
        binding.goToLogin.movementMethod = LinkMovementMethod.getInstance()
        binding.goToLogin.highlightColor = Color.TRANSPARENT

    }


    private fun checkGalleryPermissionAndPick() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permissions = arrayOf(
                Manifest.permission.READ_MEDIA_IMAGES,
            )
            requestMultiplePermissionsLauncher.launch(permissions)
        } else {
            val permission = Manifest.permission.READ_EXTERNAL_STORAGE
            if (ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED) {
                launchMediaPicker()
            } else {
                requestSinglePermissionLauncher.launch(permission)
            }
        }
    }
    private fun launchMediaPicker() {
        val mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
        mediaPickerLauncher.launch(PickVisualMediaRequest(mediaType))
    }
    private val mediaPickerLauncher = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            onCompanyLogoPicked(uri)
        } else {
            Toast.makeText(requireContext(), "No media selected", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onCompanyLogoPicked(uri: Uri) {
        binding.chooseFile.text = "Company Logo"
        binding.chooseFile.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
        binding.removeCompanyLogo.isVisible = true
        mViewModel.isCompanyLogoPicked.set(true)
        mViewModel.companyLogoUri = uri
    }

    private val requestSinglePermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) launchMediaPicker()
        else Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
    }

    private val requestMultiplePermissionsLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.values.all { it }
        if (granted) launchMediaPicker()
        else Toast.makeText(requireContext(), "Permissions denied", Toast.LENGTH_SHORT).show()
    }

}