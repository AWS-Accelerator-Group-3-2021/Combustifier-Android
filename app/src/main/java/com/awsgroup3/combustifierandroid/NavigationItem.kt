package com.awsgroup3.combustifierandroid

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object Home : NavigationItem("home", R.drawable.baseline_home_24, "Home")
    object Measurement : NavigationItem("measurement", R.drawable.baseline_straighten_24, "Measurement")
}
