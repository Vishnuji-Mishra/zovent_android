package com.app.zovent.ui.main.activity

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import com.app.zovent.R
import com.app.zovent.databinding.ActivityDashboardBinding
import com.app.zovent.databinding.ActivitySplashBinding
import com.app.zovent.ui.base.BaseActivity
import com.app.zovent.ui.main.adapter.AdapterDrawerMenu
import com.app.zovent.utils.static_data.homeSideMenuList
import com.google.android.material.shape.MaterialShapeDrawable
import kotlin.compareTo

class DashboardActivity : BaseActivity<ActivityDashboardBinding>() {
//    private lateinit var binding: ActivityDashboardBinding
    private lateinit var drawerAdapter: AdapterDrawerMenu


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        enableEdgeToEdge()
        setBottomBarPadding()
        binding.bottomNavigationView.background = null
        setBottomNavRectWithGradient()
        initializeDrawer()
    }

    private fun setBottomNavRectWithGradient() {
        // 1. Create GradientDrawable
        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT,
            intArrayOf(
                ContextCompat.getColor(this, R.color.blue_3B82F6),
                ContextCompat.getColor(this, R.color.blue_1E3A8A)
            )
        ).apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 0f // important to match the shape
        }

// 2. Create MaterialShapeDrawable with square corners
        val shapeDrawable = MaterialShapeDrawable().apply {
            shapeAppearanceModel = shapeAppearanceModel.toBuilder()
                .setAllCornerSizes(0f)
                .build()
            // Transparent so gradient shows through
            fillColor = ColorStateList.valueOf(Color.TRANSPARENT)
        }

// 3. Combine both into a LayerDrawable
        val layers = arrayOf<Drawable>(gradientDrawable, shapeDrawable)
        val layerDrawable = LayerDrawable(layers)

// 4. Apply as background
        binding.bottomAppBar.background = layerDrawable
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_dashboard // Ensure this points to the correct layout resource
    }
    private fun initializeDrawer() {
        if (!::drawerAdapter.isInitialized) {
            binding.drawerRecyclerView.adapter = AdapterDrawerMenu(homeSideMenuList)
        }
        binding.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}

            override fun onDrawerOpened(drawerView: View) {

            }

            override fun onDrawerClosed(drawerView: View) {}

            override fun onDrawerStateChanged(newState: Int) {}
        })
    }
    private fun setBottomBarPadding() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Remove extra padding from BottomAppBar and its children
            binding.bottomAppBar.setPadding(0, 0, 0, 0)
            val offset = if (isGestureNavigation()) systemBars.bottom else 0
            binding.bottomNavigationView.setPadding(0, 0, 0, offset)
            WindowInsetsCompat.CONSUMED
        }
    }
    private fun isGestureNavigation(): Boolean {
        val resId = resources.getIdentifier("config_navBarInteractionMode", "integer", "android")
        return resId > 0 && resources.getInteger(resId) == 2
    }

    //    private fun setBottomBarPadding() {
//        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            // Remove extra padding from BottomAppBar and its children
//            binding.bottomAppBar.setPadding(0, 0, 0, 0)
//            val offset = if (isGestureNavigation()) systemBars.bottom else getNavigationBarHeight()
//            binding.bottomNavigationView.setPadding(0, 0, 0, offset)
//            WindowInsetsCompat.CONSUMED
//        }
//    }
//    private fun isGestureNavigation(): Boolean {
//        val resId = resources.getIdentifier("config_navBarInteractionMode", "integer", "android")
//        return resId > 0 && resources.getInteger(resId) == 2
//    }
    private fun getNavigationBarHeight(): Int {
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) resources.getDimensionPixelSize(resourceId) else 0
    }

    fun openDrawer() {
        binding.drawerLayout.openDrawer(GravityCompat.START)
    }
}