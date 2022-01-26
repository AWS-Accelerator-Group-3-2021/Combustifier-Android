package com.awsgroup3.combustifier

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.awsgroup3.combustifier.ui.theme.CombustifierTheme
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import kotlin.math.roundToInt


@OptIn(ExperimentalCoilApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AfterCameraScreen(image: Uri?, confidence: String, combustibility: String) {

    Log.d("UriInput", image.toString())
    CombustifierTheme() {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberImagePainter(data = image),
                contentDescription = null,
                modifier = Modifier.size(340.dp)
            )
            Text(
                text = confidence,
                modifier = Modifier.padding(24.dp)
            )
            Text(
                text = combustibility,
                modifier = Modifier.padding(24.dp)
            )
        }
    }
}

class SendImageActivity : ComponentActivity() {

    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val imageUri = intent.getParcelableExtra<Uri>("imageUri")
        setContent {
            CombustifierTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AfterCameraScreen(imageUri, confidence = "sending", combustibility = "none")
                    if (imageUri?.let { b64encode(it) } != null) {
                        b64encode(imageUri)?.let { Log.d("b64encode", it) }
                        val base64string = b64encode(imageUri)
                        // use Volley to send base64string
                        if (base64string != null) {
                            val queue = Volley.newRequestQueue(this)
                            val url = "http://54.162.14.154:8080/upload"
                            val jsonBody = JSONObject()

                            jsonBody.put("img", base64string)
                            val request = JsonObjectRequest(
                                Request.Method.POST, url, jsonBody,
                                { response ->
                                    val confidence = response.getDouble("confidence").roundToInt()
                                    val combustibility = response.getString("combustibility")
                                    Log.d("confidence", confidence.toString())
                                    Log.d("combustibility", combustibility)
                                    val intent = Intent(this, ImageSentActivity::class.java)
                                    intent.putExtra("confidence", "Confidence Score:${confidence}%")
                                    intent.putExtra("combustibility", "Combustible?: $combustibility")
                                    intent.putExtra("imageUri", imageUri)
                                    startActivity(intent)
                                },
                                { error ->
                                    val intent = Intent(this, ImageSentActivity::class.java)
                                    intent.putExtra("response", error.toString())
                                    startActivity(intent)
                                    Log.d("Error", error.toString())
                                }
                            )
                            Log.d("request", request.toString())
                            val TIMEOUT_MS = 10000
                            request.retryPolicy = DefaultRetryPolicy(
                                TIMEOUT_MS,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                            )
                            queue.add(request)

                        }


                    }


                }
            }
        }
    }

}

fun b64encode(uri: Uri): String? {
    val bm = BitmapFactory.decodeFile(uri.path)
    val baos = ByteArrayOutputStream()
    bm.compress(Bitmap.CompressFormat.JPEG, 80, baos) // bm is the bitmap object
    val b = baos.toByteArray()
    var base64str = Base64.encodeToString(b, Base64.DEFAULT)
    return base64str
}

class ImageSentActivity : ComponentActivity() {

    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        val confidence = intent.getStringExtra("confidence")
        val combustibility = intent.getStringExtra("combustibility")
        super.onCreate(savedInstanceState)
        val imageUri = intent.getParcelableExtra<Uri>("imageUri")
        setContent {
            CombustifierTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    if (confidence != null) {
                        if (combustibility != null) {
                            AfterCameraScreen(
                                imageUri,
                                confidence = confidence,
                                combustibility = combustibility
                            )
                        }
                    }
                    }
                }
            }
        }
    }

