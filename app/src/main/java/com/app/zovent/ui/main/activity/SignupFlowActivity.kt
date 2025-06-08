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

class SignupFlowActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup_flow)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.signup_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        handleActivityBackButton()

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