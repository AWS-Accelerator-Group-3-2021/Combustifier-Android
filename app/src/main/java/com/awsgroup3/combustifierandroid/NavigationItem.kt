package com.awsgroup3.combustifierandroid
import android.R.drawable.ic_menu_day

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object Home : NavigationItem("home", ic_menu_day, "Home")
    object Measurement : NavigationItem("measurement", ic_menu_day, "Measurement")
}
