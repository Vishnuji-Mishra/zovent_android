package com.app.zovent.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.zovent.R
import com.app.zovent.databinding.AdapterDrawerMenuBinding
import com.app.zovent.utils.static_data.MenuListModel
import kotlin.math.PI

class AdapterDrawerMenu(val list: List<MenuListModel>): RecyclerView.Adapter<AdapterDrawerMenu.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(AdapterDrawerMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.binding.apply {
            title.text = list[position].title
            if (position==0){
                title.setCompoundDrawablesWithIntrinsicBounds(list[position].icon, 0, R.drawable.down_arrow_blue, 0)
            }else{
                title.setCompoundDrawablesWithIntrinsicBounds(list[position].icon, 0, 0, 0)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(val binding: AdapterDrawerMenuBinding): RecyclerView.ViewHolder(binding.root)
}