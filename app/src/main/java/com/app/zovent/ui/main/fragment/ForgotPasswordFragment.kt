package com.app.zovent.ui.main.fragment

import android.widget.Toast
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.zovent.R
import com.app.zovent.databinding.FragmentForgotPasswordBinding
import com.app.zovent.ui.base.BaseFragment
import com.app.zovent.ui.main.view_model.ForgotPasswordViewModel
import kotlin.getValue

class ForgotPasswordFragment : BaseFragment<FragmentForgotPasswordBinding, ForgotPasswordViewModel>(R.layout.fragment_forgot_password) {
    override val binding: FragmentForgotPasswordBinding by viewBinding(FragmentForgotPasswordBinding::bind)
    override val mViewModel: ForgotPasswordViewModel by viewModels()

    override fun isNetworkAvailable(boolean: Boolean) {}

    override fun setupViewModel() {}

    override fun setupViews() {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mViewModel
        }
    }

    override fun setupObservers() {
        mViewModel.validationMessage.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }


}