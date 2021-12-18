package com.awsgroup3.combustifier

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.Center
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.awsgroup3.combustifier.ui.theme.CombustifierTheme

@OptIn(ExperimentalCoilApi::class, androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun AfterCameraScreen(image: Bitmap){
    CombustifierTheme() {
        Column(Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberImagePainter(image),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}