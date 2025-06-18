package com.app.zovent.utils.static_data

import com.app.zovent.R

val homeCountDataListColors = listOf<Int>(
    R.color.blue_E0F7FA,
    R.color.green_E8F5E9,
    R.color.orange_FFF3E0,
    R.color.red_FFEBEE,
    R.color.blue_E3F2FD,
)

data class MenuListModel(val title: String, val icon: Int)

val homeSideMenuList =
    listOf<MenuListModel>(
        MenuListModel(title = "Invoice", icon = R.drawable.invoice_icon),
        MenuListModel(title = "Customer", icon = R.drawable.customer_icon),
        MenuListModel(title = "Purchases", icon = R.drawable.purchases_icon),
        MenuListModel(title = "Expiry Tracking", icon = R.drawable.expiry_tracking_icon),
        MenuListModel(title = "Medication due list", icon = R.drawable.medication_due_list_icon),
        MenuListModel(title = "About Us", icon = R.drawable.about_us_icon),
        MenuListModel(title = "FAQS", icon = R.drawable.faqs_icon),
    )