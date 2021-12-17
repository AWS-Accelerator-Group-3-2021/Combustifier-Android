package com.awsgroup3.combustifier

import android.content.Intent
import android.graphics.Camera
import android.graphics.Paint
import android.provider.MediaStore
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType.Companion.Uri
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
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

@OptIn(ExperimentalCoilApi::class)
@Composable
fun CameraScreen(navController: NavController, modifier: Modifier = Modifier) {
    CombustifierTheme {
            val emptyImageUri = android.net.Uri.parse("file://dev/null")
            var imageUri by remember { mutableStateOf(emptyImageUri) }
            if (imageUri != emptyImageUri) {
                Box(modifier = modifier) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = rememberImagePainter(imageUri),
                        contentDescription = "Captured image"
                    )
                    Button(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        onClick = {
                            imageUri = emptyImageUri
                        }
                    ) {
                        Text("Remove image")
                    }
                }
            } else {
                CameraCapture(
                    modifier = modifier,
                    onImageFile = { file ->
                        imageUri = file.toUri()
                    }
                )
            }
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




