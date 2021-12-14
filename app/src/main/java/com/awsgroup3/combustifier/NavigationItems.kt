package com.awsgroup3.combustifier


sealed class NavigationItem(var route: String, var title: String) {
    object Home : NavigationItem("home", "Home")
    object Measurement : NavigationItem("measurement", "Measurement")
}
