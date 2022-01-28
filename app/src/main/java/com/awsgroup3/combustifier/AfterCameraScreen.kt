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
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import kotlin.random.Random


@OptIn(ExperimentalCoilApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AfterCameraScreen(image: Uri?, confidence: String, combustibility: String) {

    var combustibility_str: String
    if (combustibility == "Yes") {
        Particles(emoji = "\uD83D\uDD25", modifier = Modifier
            .fillMaxWidth()
            .height(400.dp), visible = true, quantity = 22)
        combustibility_str = "This object is Combustible"
    } else if (combustibility == "No") {
        combustibility_str = "This object is Incombustible"
    } else {
        combustibility_str = ""
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
                text = combustibility_str,
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
    fun storeImageData(key: String, value: JSONObject) {
        Log.d("imagekey", key)
        Log.d("imagevalue", value["combustibility"].toString())
    }

    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var imageUri = intent.getParcelableExtra<Uri>("imageUri")
        // get file name from imageuri
        val fileName = imageUri.toString().split("/").last()
        Log.d("filename", fileName)
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
                        b64encode(imageUri!!)?.let { Log.d("b64encode", it) }
                        val base64string = b64encode(imageUri!!)
                        // use Volley to send base64string
                        if (base64string != null) {
                            val queue = Volley.newRequestQueue(this)
                            val url = "http://54.209.249.162:8080/upload"
                            val jsonBody = JSONObject()

                            jsonBody.put("img", base64string)
                            val request = JsonObjectRequest(
                                Request.Method.POST, url, jsonBody,
                                { response ->
                                    confidence =
                                        response.getDouble("confidence").roundToInt().toString()
                                    combustibility = response.getString("combustibility")
                                    storeImageData(fileName, response)
                                    // rename file
                                    val file = File(imageUri!!.path)
                                    val renamedFile = File(
                                        file.parent,
                                        "${file.nameWithoutExtension}_${confidence}_${combustibility}.jpg"
                                    )
                                    file.renameTo(renamedFile)
                                    // change imageUri to renamed file
                                    imageUri = Uri.fromFile(renamedFile)
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
                                confidence = "An error occurred while analysing your image"
                            }
                            Log.d("request", request.toString())
                            val TIMEOUT_MS = 16000
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
    bm.compress(Bitmap.CompressFormat.JPEG, 55, baos) // bm is the bitmap object
    //log the size
    val b = baos.toByteArray()
    //log size of b
    Log.d("size of b", b.size.toString())
    var base64str = Base64.encodeToString(b, Base64.DEFAULT)
    return base64str
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Particles(
    modifier: Modifier,
    quantity: Int,
    emoji: String,
    visible: Boolean
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(
                durationMillis = MAX_ANIMATION_DURATION.toInt() - 300,
                easing = LinearEasing,
                delayMillis = 300
            )
        ),
        exit = ExitTransition.None
    ) {
        val particles = remember { calculateParticleParams(quantity, emoji) }
        val transitionState = remember {
            MutableTransitionState(MIN_HEIGHT).apply {
                targetState = MAX_HEIGHT
            }
        }
        val transition = updateTransition(transitionState, label = "height transition")
        val height by transition.animateInt(
            transitionSpec = {
                tween(
                    durationMillis = MAX_ANIMATION_DURATION.toInt(),
                    easing = LinearOutSlowInEasing
                )
            },
            label = "height animation of particles"
        ) { it }

        Layout(
            modifier = modifier.padding(bottom = 50.dp),
            content = {
                for (i in 0 until quantity) {
                    Particle(particles[i])
                }
            }
        ) { measurables, constraints ->
            val placeables = measurables.map { it.measure(constraints) }
            layout(constraints.maxWidth, height) {
                placeables.forEachIndexed { index, placeable ->
                    val params = particles[index]
                    placeable.placeRelative(
                        x = (params.horizontalFraction * constraints.maxWidth).toInt() - constraints.maxWidth / 2,
                        y = (params.verticalFraction * height).toInt() - height / 2
                    )
                }
            }
        }
    }
}

private fun calculateParticleParams(quantity: Int, emoji: String): List<ParticleModel> {
    val random = Random(System.currentTimeMillis().toInt())
    val result = mutableListOf<ParticleModel>()
    for (i in 0 until quantity) {
        val verticalFraction = random.nextDouble(from = 0.0, until = 1.0).toFloat()
        val horizontalFraction = random.nextDouble(from = 0.0, until = 1.0).toFloat()

        val model =
            ParticleModel(
                verticalFraction = verticalFraction,
                horizontalFraction = horizontalFraction,
                initialScale = lerp(MIN_PARTICLE_SIZE, MAX_PARTICLE_SIZE, verticalFraction),
                duration = lerp(
                    MIN_ANIMATION_DURATION,
                    MAX_ANIMATION_DURATION,
                    verticalFraction
                ).toInt(),
                emoji = emoji
            )
        result.add(
            model
        )
    }

    return result

}

private fun lerp(start: Float, stop: Float, fraction: Float) =
    (start * (1 - fraction) + stop * fraction)

@Composable
private fun Particle(model: ParticleModel) {
    val transitionState = remember {
        MutableTransitionState(0.1f).apply {
            targetState = 0f
        }
    }

    val targetScale = remember { model.initialScale * TARGET_PARTICLE_SCALE_MULTIPLIER }

    val transition = updateTransition(transitionState, label = "particle transition")

    val alpha by transition.animateFloat(
        transitionSpec = {
            keyframes {
                durationMillis = model.duration
                0.1f at START_OF_ANIMATION
                1f at (model.duration * 0.1f).toInt()
                1f at (model.duration * 0.8f).toInt()
                0f at model.duration
            }
        },
        label = "alpha animation of particle"
    ) { it }

    val scale by transition.animateFloat(
        transitionSpec = {
            keyframes {
                durationMillis = model.duration
                model.initialScale at START_OF_ANIMATION
                model.initialScale at (model.duration * 0.7f).toInt()
                targetScale at model.duration
            }
        },
        label = "scale animation of particle"
    ) { it }

    Text(
        modifier = Modifier
            .wrapContentSize()
            .scale(scale)
            .alpha(alpha),
        text = model.emoji,
        fontSize = PARTICLE_TEXT_SIZE.sp
    )
}

private const val TARGET_PARTICLE_SCALE_MULTIPLIER = 1.3f
private const val START_OF_ANIMATION = 0
private const val MIN_PARTICLE_SIZE = 1f
private const val MAX_PARTICLE_SIZE = 2.6f
private const val MIN_ANIMATION_DURATION = 1200f
private const val MAX_ANIMATION_DURATION = 1500f
private const val PARTICLE_TEXT_SIZE = 14
private const val MIN_HEIGHT = 300
private const val MAX_HEIGHT = 800

data class ParticleModel(
    val verticalFraction: Float,
    val horizontalFraction: Float,
    val initialScale: Float,
    val duration: Int,
    val emoji: String
)