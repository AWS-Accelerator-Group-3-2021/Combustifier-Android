package com.awsgroup3.combustifier

import androidx.annotation.StringRes

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Home : Screen("home", R.string.Home)
    object Measurement : Screen("measurement", R.string.Measurement)
}
