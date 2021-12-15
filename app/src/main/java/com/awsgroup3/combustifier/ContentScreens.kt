package com.awsgroup3.combustifier

import android.graphics.Paint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.awsgroup3.combustifier.ui.theme.CombustifierTheme

@ExperimentalMaterial3Api
@Composable
fun HomeScreen() {
    CombustifierTheme {
        Scaffold(
            topBar = { TopAppBar(pageName = "Home") },
            content = {}
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            Text(
                text = "Home View Test"
            )
            ElevatedButton(
                onClick = {println("Button was pressed")}
            ) {
                Icon(Icons.Filled.Add, contentDescription = null)
                Text("New Check")
            }
        }
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