package com.app.zovent.ui.base

import android.R
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.app.zovent.utils.LogUtil
import com.app.zovent.utils.network.NetworkStatus
import com.app.zovent.utils.network.NetworkStatusHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


abstract class BaseFragment<Binding : ViewBinding, ViewModel : BaseViewModel>(layoutID: Int) :
    Fragment(layoutID) {
    protected abstract val binding: Binding
    protected abstract val mViewModel: ViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //observeConnectionLiveData()
        CoroutineScope(Dispatchers.Main).launch {
            setupViews()
            setupViewModel()
            setupObservers()
        }


    }
    open fun hasPermission(str: String?): Boolean {
        return Build.VERSION.SDK_INT < 23 || requireActivity().checkSelfPermission(str!!) == PackageManager.PERMISSION_GRANTED
    }

    open fun requestPermissionsSafely(strArr: Array<String?>?, i: Int) {
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(strArr!!, i)
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

    fun alertDailog(context: Context,message: String)
    {
        var alertDialog = AlertDialog.Builder(context) //set icon
            .setIcon(R.drawable.ic_dialog_alert) //set title
            .setTitle("") //set message
            .setMessage(message) //set positive button
            .setPositiveButton("ok") { dialogInterface, i -> //set what would happen when positive button is clicked
                requireActivity().finish()
            } //set negative button
           /* .setNegativeButton("No") { dialogInterface, i -> //set what should happen when negative button is clicked
                Toast.makeText(
                    ApplicationProvider.getApplicationContext<Context>(),
                    "Nothing Happened",
                    Toast.LENGTH_LONG
                ).show()
            }*/
            .show()
    }


    /*  fun onbackPressDialog(context1: Context) {
        val alertDialog1: AlertDialog
        val alertDialogBuilder1: AlertDialog.Builder
        val combatExitDailogBinding: CombatExitDailogBinding = DataBindingUtil.bind(
            LayoutInflater.from(
                context
            ).inflate(R.layout.combat_exit_dailog, null)
        )
        alertDialogBuilder1 = AlertDialog.Builder(context!!)
        alertDialogBuilder1.setView(combatExitDailogBinding.getRoot())
        alertDialogBuilder1.setCancelable(false)
        alertDialog1 = alertDialogBuilder1.create()
        alertDialog1.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        // show it
        alertDialog1.show()
        combatExitDailogBinding.content.setText("Are you sure you want to Exit?")
        combatExitDailogBinding.crossIcon.setOnClickListener { v -> alertDialog1.dismiss() }
        combatExitDailogBinding.No.setOnClickListener { view -> alertDialog1.dismiss() }
        combatExitDailogBinding.yes.setOnClickListener { view ->
            context1.startActivity(Intent(context1, HomeActivity::class.java))
            alertDialog1.dismiss()
        }
    }*/


}