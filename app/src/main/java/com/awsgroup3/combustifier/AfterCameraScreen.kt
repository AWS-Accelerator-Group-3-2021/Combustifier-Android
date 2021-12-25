package com.awsgroup3.combustifier

import android.graphics.Bitmap
import android.os.Bundle
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
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.awsgroup3.combustifier.ui.theme.CombustifierTheme


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
    private val _awsResponse = MutableLiveData("")
    val awsResponse: LiveData<String> = _awsResponse

    fun onResponseChange(new: String) {
        _awsResponse.value = new
    }
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
