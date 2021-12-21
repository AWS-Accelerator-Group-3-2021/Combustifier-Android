package com.awsgroup3.combustifier

import android.R.attr.bitmap
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.awsgroup3.combustifier.ui.theme.CombustifierTheme
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*


@OptIn(ExperimentalCoilApi::class, androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun AfterCameraScreen(image: Bitmap?) {
    CombustifierTheme() {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberImagePainter(image),
                contentDescription = null,
                modifier = Modifier.size(340.dp)
            )
        }
    }
}

class SendImageActivity : ComponentActivity() {
    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val imageBitmap = intent.getParcelableExtra<Bitmap>("imageBitmap")
        setContent {
            CombustifierTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colorScheme.background) {
                    AfterCameraScreen(imageBitmap)


                }
            }
        }
    }
}
