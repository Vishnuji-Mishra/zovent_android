package com.app.zovent.ui.main.fragment

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.zovent.R
import com.app.zovent.databinding.FragmentForgotPasswordBinding
import com.app.zovent.ui.base.BaseFragment
import com.app.zovent.ui.main.activity.DashboardActivity
import com.app.zovent.ui.main.view_model.ForgotPasswordViewModel
import com.app.zovent.utils.ProcessDialog
import com.app.zovent.utils.Status
import com.app.zovent.utils.StatusCode
import com.google.gson.Gson
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
        mViewModel.forgotPasswordResponse.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        ProcessDialog.dismissDialog()
                        Log.i("TAG", "setupObservers: " + Gson().toJson(result.data))
                        Toast.makeText(requireContext(), result.data?.message, Toast.LENGTH_SHORT).show()
                        findNavController().navigate(
                            ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToOTPFragment2(
                                from = "forgot",
                                email = mViewModel.email.get()?.trim() ?: ""
                            )
                        )
                    }
                    Status.LOADING -> ProcessDialog.startDialog(requireContext())
                    Status.ERROR -> ProcessDialog.dismissDialog()
                }
            }
        }
    }
}