package com.app.zovent.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.viewbinding.ViewBinding
import com.app.zovent.utils.LogUtil
import com.app.zovent.utils.network.NetworkStatus
import com.app.zovent.utils.network.NetworkStatusHelper
import com.app.zovent.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseBottomSheetDailogFragment<Binding : ViewBinding, ViewModel : BaseViewModel>(layoutID: Int) : BottomSheetDialogFragment() {

    protected lateinit var binding: Binding
    protected abstract val mViewModel: ViewModel

    @LayoutRes
    protected abstract fun getLayout(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //observeConnectionLiveData()

        CoroutineScope(Dispatchers.Main).launch {
            setupViews()
            setupViewModel()
            setupObservers()
        }


    }

    private fun observeConnectionLiveData() {
        NetworkStatusHelper(requireContext()).observe(viewLifecycleOwner) {
            when (it) {
                NetworkStatus.Available -> {
                    isNetworkAvailable(true)
                    LogUtil.e("Network", "Network Connection Established")
                }
                NetworkStatus.Unavailable -> {
                    LogUtil.e("Network", "No Internet")
                    isNetworkAvailable(false)

                }
            }
        }


    }

    abstract fun isNetworkAvailable(boolean: Boolean)
    abstract fun setupViewModel()
    abstract fun setupViews()
    abstract fun setupObservers()

    override fun getTheme(): Int = R.style.AppBottomSheetDialogTheme

}