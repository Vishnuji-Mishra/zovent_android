package com.app.zovent.ui.main.fragment

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.zovent.R
import com.app.zovent.databinding.FragmentHomeBinding
import com.app.zovent.databinding.FragmentOTPBinding
import com.app.zovent.databinding.FragmentOTPBinding.bind
import com.app.zovent.ui.base.BaseFragment
import com.app.zovent.ui.main.activity.DashboardActivity
import com.app.zovent.ui.main.activity.SignupFlowActivity
import com.app.zovent.ui.main.adapter.AdapterDashboardCountData
import com.app.zovent.ui.main.custom.CustomMarkerView
import com.app.zovent.ui.main.custom.GenericKeyEvent
import com.app.zovent.ui.main.custom.GenericTextWatcher
import com.app.zovent.ui.main.view_model.HomeViewModel
import com.app.zovent.ui.main.view_model.OTPViewModel
import com.app.zovent.utils.Preferences
import com.app.zovent.utils.static_data.homeCountDataListColors
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.ColorTemplate
import kotlin.getValue

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {
    override val binding: FragmentHomeBinding by viewBinding(FragmentHomeBinding::bind)
    override val mViewModel: HomeViewModel by viewModels()

    override fun isNetworkAvailable(boolean: Boolean) {}

    override fun setupViewModel() {

    }

    override fun setupViews() {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mViewModel
            clickEvent = ::clickEvent
            countDataRecycler.adapter = AdapterDashboardCountData(homeCountDataListColors)
            setUpSalesActivityGraph()
            setupPieChart()
            setupGraphSpinner()
        }
    }

    private fun tempLogout() {
        Preferences.removeAllPreferencesExcept(requireContext(), listOf())
        val intent = Intent(requireContext(), SignupFlowActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        requireActivity().finish()
    }

    fun clickEvent(click: Int){
        when(click){
            0->{
                (activity as? DashboardActivity)?.openDrawer()
            }
            1->{
                tempLogout()
            }
        }
    }
    private fun setupGraphSpinner() {
        val options = listOf("Choose date", "Today", "7 days", "15 days", "30 days")
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item_graph, options)
        binding.spinnerDateRange.adapter = adapter

    }

    private fun setUpSalesActivityGraph() {
        val entries = listOf(
            Entry(0f, 10f),
            Entry(1f, 50f),
            Entry(2f, 100f),
            Entry(3f, 150f),
            Entry(4f, 500f),
            Entry(5f, 1000f),
        )

        val dataSet = LineDataSet(entries, "").apply {
            color = ContextCompat.getColor(requireContext(), R.color.blue_04BFDA)
            lineWidth = 2f
            setDrawCircles(false)
            setDrawValues(false)
            mode = LineDataSet.Mode.CUBIC_BEZIER

            // Highlight settings
            highLightColor = Color.parseColor("#FFA500") // orange vertical line
            setDrawHorizontalHighlightIndicator(false)
            setDrawHighlightIndicators(true)
        }

        dataSet.setDrawVerticalHighlightIndicator(true)
        dataSet.highlightLineWidth = 1f
        dataSet.highLightColor = Color.parseColor("#FFA500") // orange



        val lineData = LineData(dataSet)

        binding.lineChart.apply {
            data = lineData
            axisRight.isEnabled = false
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
            axisLeft.setDrawGridLines(true)
            xAxis.granularity = 1f
            xAxis.valueFormatter = IndexAxisValueFormatter(
                listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
            )
            description.isEnabled = false
            legend.isEnabled = false
            highlightValue(Highlight(4f, 140f, 0))
            setDrawMarkers(true)
            invalidate()
        }

        val marker = CustomMarkerView(requireContext(), R.layout.custom_marker_view)
        marker.chartView = binding.lineChart
        binding.lineChart.marker = marker


    }

    fun setupPieChart() {
        val entries = listOf(
            PieEntry(50f, "a"),
            PieEntry(40f, "b"),
            PieEntry(60f, "c"),
            PieEntry(30f, "d"),
            PieEntry(20f, "e"),
            PieEntry(70f, "f"),
            PieEntry(71f, "g")
        )

        val dataSet = PieDataSet(entries, "").apply {
            colors = listOf(
                Color.parseColor("#FF6F61"),
                Color.parseColor("#6B5B95"),
                Color.parseColor("#88B04B"),
                Color.parseColor("#FFA07A"),
                Color.parseColor("#20B2AA"),
                Color.parseColor("#FFD700"),
                Color.parseColor("#DA70D6")
            )
//            sliceSpace = 3f
            setDrawValues(false) // hide slice values
        }

        val pieData = PieData(dataSet)
        val spannable = SpannableString("341").apply {
            setSpan(StyleSpan(Typeface.BOLD), 0, length, 0)
        }
        binding.pieChart.apply {
            data = pieData
            description.isEnabled = false
            setDrawEntryLabels(false)
            legend.isEnabled = true // no tags/listing
            legend.form = Legend.LegendForm.CIRCLE
            isDrawHoleEnabled = true
            legend.isWordWrapEnabled = true
            legend.setDrawInside(false) // Ensures it draws below the chart, not on it
            holeRadius = 40f
            transparentCircleRadius = 50f // gives a light 3D feel
            setHoleColor(Color.WHITE)
            centerText = spannable
            setCenterTextSize(18f)
            setCenterTextColor(Color.BLACK)
            animateY(1400, Easing.EaseInOutQuad)
            invalidate()
        }
    }

    override fun setupObservers() {}
}