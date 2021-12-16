package com.awsgroup3.combustifier

import android.graphics.Paint
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.awsgroup3.combustifier.ui.theme.CombustifierTheme

@ExperimentalMaterial3Api
@Composable
fun HomeScreen(navController: NavController) {
    CombustifierTheme {
        Scaffold(
            topBar = { TopAppBar(pageName = "Home") },
            bottomBar = {
                NewCheckButton(navController)
            },
            content = {}
        )
    }
}

@Composable
fun CameraScreen(navController: NavController) {
    CombustifierTheme() {
        Text("Hello!")
    }
}


@ExperimentalMaterial3Api
@Composable
fun MeasurementScreen(navController: NavController) {
    CombustifierTheme {
        Scaffold(
            topBar = { TopAppBar(pageName = "Measurement") },
            content = {
                Text(
                    text = "Measurement View Test"
                )
            })
    }
}




