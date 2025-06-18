package com.app.zovent.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.zovent.R
import com.app.zovent.databinding.AdapterDashboardCountDataBinding

class AdapterDashboardCountData(val list:List<Int>): RecyclerView.Adapter<AdapterDashboardCountData.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(AdapterDashboardCountDataBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.binding.apply {
            mainLayout.setBackgroundColor(root.context.resources.getColor(list[position]))
        }
    }

    override fun getItemCount(): Int {
        return 5
    }

    inner class ViewHolder(val binding: AdapterDashboardCountDataBinding): RecyclerView.ViewHolder(binding.root)
}