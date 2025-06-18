package com.app.zovent.ui.main.activity

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.app.zovent.R
import com.app.zovent.databinding.ActivitySignupFlowBinding
import com.app.zovent.ui.base.BaseActivity

class SignupFlowActivity : BaseActivity<ActivitySignupFlowBinding>() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.signup_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        handleActivityBackButton()

    }
    override fun getLayoutId(): Int {
        return R.layout.activity_signup_flow // Ensure this points to the correct layout resource
    }
    private fun handleActivityBackButton() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val currentDestinationId = navController.currentDestination?.id

                if (currentDestinationId == R.id.signinFragment) {
                    finish()
                } else{
                    navController.popBackStack()
                }
            }
        })
    }
}