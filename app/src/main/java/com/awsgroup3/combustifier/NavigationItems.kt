package com.awsgroup3.combustifier

import androidx.navigation.Navigation


sealed class NavigationItem(var route: String, var title: String) {
    object Home : NavigationItem("home", "Home")
    object Measurement : NavigationItem("measurement", "Measurement")
    object Camera : NavigationItem("camera", "Camera")
}
