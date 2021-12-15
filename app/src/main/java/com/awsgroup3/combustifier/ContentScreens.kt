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
import com.awsgroup3.combustifier.ui.theme.CombustifierTheme

@ExperimentalMaterial3Api
@Composable
fun HomeScreen() {
    CombustifierTheme {
        Scaffold(
            topBar = { TopAppBar(pageName = "Home") },
            content = {}
        )
    }
}



@ExperimentalMaterial3Api
@Composable
fun MeasurementScreen() {
    CombustifierTheme {
        Scaffold(
            topBar = { TopAppBar(pageName = "Measurement") },
            content = {}
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            Text(
                text = "Measurement View Test"
            )
        }
    }
}


@Composable
fun CameraScreen() {
}