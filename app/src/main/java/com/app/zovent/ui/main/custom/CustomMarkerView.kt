package com.app.zovent.ui.main.custom

import android.content.Context
import android.widget.TextView
import com.app.zovent.R
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF

class CustomMarkerView(
    context: Context,
    layoutResource: Int
) : MarkerView(context, layoutResource) {

    private val tvPrice: TextView = findViewById(R.id.tvPrice)

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        tvPrice.text = "₹" + e?.y?.toInt().toString()
        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF(-(width / 2f), -height.toFloat()) // center above point
    }
}
