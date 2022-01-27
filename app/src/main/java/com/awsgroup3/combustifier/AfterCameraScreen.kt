package com.awsgroup3.combustifier

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Base64
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.awsgroup3.combustifier.ui.theme.CombustifierTheme
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import kotlin.math.roundToInt


@OptIn(ExperimentalCoilApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AfterCameraScreen(image: Uri?, confidence: String, combustibility: String) {
    var combustibility_str: String
    if(combustibility=="Yes") {
        combustibility_str = "This object is Combustible"
    }
    else {
        combustibility_str = "This object is Incombustible"
    }
    val context = LocalContext.current
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
                text = "Confidence Score: $confidence",
                modifier = Modifier.padding(24.dp)
            )
            Text(
                text = "This object is $combustibility_str",
                modifier = Modifier.padding(24.dp)
            )
            if (combustibility != "") {
                ElevatedButton(
                    onClick = {
                        val intent = Intent(context, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(intent)
                    }) {
                    Text("Go Back")
                }
            }
        }
    }
}

class SendImageActivity : ComponentActivity() {
    fun saveToKeyValue(key: String, value: Any?) {
        Log.d("KeyValue", key)
        val json = JSONObject()
        json.put(key, value)
        val file = File(this.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "keyValueStore.json")
        if (!file.exists()) {
            file.createNewFile()
        }
        file.writeText(json.toString())
        Log.d("keyvaluestore",file.toString())
    }
    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val imageUri = intent.getParcelableExtra<Uri>("imageUri")
        setContent {
            var confidence by remember { mutableStateOf("Sending your image...") }
            var combustibility by remember { mutableStateOf("") }
            CombustifierTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AfterCameraScreen(
                        imageUri,
                        confidence = confidence,
                        combustibility = combustibility
                    )
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
                                    confidence = response.getDouble("confidence").roundToInt().toString()
                                    combustibility = response.getString("combustibility")
                                    saveToKeyValue(imageUri.toString(), response)
                                    Log.d("confidence", confidence)
                                    Log.d("combustibility", combustibility)
                                }
                            ) { error ->
                                Log.d("Error", error.toString())
                                // delete the file using URI
                                imageUri.let {
                                    val file = File(
                                        this.getExternalFilesDir(
                                            Environment.DIRECTORY_PICTURES
                                        ), it.toString()
                                    )
                                    file.delete()
                                }
                                combustibility = "An error occurred while analysing your image"
                            }
                            Log.d("request", request.toString())
                            val TIMEOUT_MS = 6000
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
        //val confidence = intent.getStringExtra("confidence")
        //val combustibility = intent.getStringExtra("combustibility")
        super.onCreate(savedInstanceState)
        val imageUri = intent.getParcelableExtra<Uri>("imageUri")
        setContent {
            CombustifierTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    //if (confidence != null) {
                    //if (combustibility != null) {
                    // AfterCameraScreen(
                    //   imageUri,
                    //   confidence = confidence,
                    //  combustibility = combustibility
                    // )
                }
            }
        }
    }
}



