package com.awsgroup3.combustifier

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.awsgroup3.combustifier.ui.theme.CombustifierTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@OptIn(ExperimentalPermissionsApi::class)
@ExperimentalMaterial3Api
@Composable
fun HomeScreen(navController: NavController) {
    CombustifierTheme {
        Scaffold(
            topBar = { TopAppBar(pageName = "Home") },
            floatingActionButton = {
                NewCheckButton()
            },
            floatingActionButtonPosition = FabPosition.End,
            content = {
                Column() {

                }
            }
        )
    }
}

@OptIn(ExperimentalCoilApi::class,
    com.google.accompanist.permissions.ExperimentalPermissionsApi::class
)
@Composable
fun CameraScreen(navController: NavController) {

    val result = remember { mutableStateOf<Bitmap?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
        result.value = it
    }

    ElevatedButton(onClick = { launcher.launch() }) {
        Text(text = "Take a picture")
    }

    result.value?.let { image ->
        AfterCameraScreen(image)
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




