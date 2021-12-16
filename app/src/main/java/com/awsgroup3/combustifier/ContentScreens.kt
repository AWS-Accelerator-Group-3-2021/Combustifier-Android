package com.awsgroup3.combustifier

import android.content.Intent
import android.graphics.Camera
import android.graphics.Paint
import android.provider.MediaStore
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.navigation.NavController
import com.awsgroup3.combustifier.ui.theme.CombustifierTheme

@ExperimentalMaterial3Api
@Composable
fun HomeScreen(navController: NavController) {
    CombustifierTheme {
        Scaffold(
            topBar = { TopAppBar(pageName = "Home") },
            floatingActionButton = {
                NewCheckButton(navController)
            },
            floatingActionButtonPosition = FabPosition.End,
            content = {
                Column() {

                }
            }
        )
    }
}

@Composable
fun CameraScreen(navController: NavController) {
    CombustifierTheme {
        CameraPermission()
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




