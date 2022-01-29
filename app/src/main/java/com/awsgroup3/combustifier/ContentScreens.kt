package com.awsgroup3.combustifier

import android.content.Intent
import android.os.Environment
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.awsgroup3.combustifier.ui.theme.CombustifierTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import java.io.File


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
                ImageCardColumn()
            }
        )
    }
}


@ExperimentalMaterial3Api
@Composable
fun MeasurementScreen(navController: NavController) {
    val intent = Intent(LocalContext.current, Measurement::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    CombustifierTheme {
        //wait a few seconds before launching the activity
        ContextCompat.startActivity(LocalContext.current, intent, null)
    }
}


@ExperimentalMaterial3Api
@Composable
fun ImageCard(picture: File, combustibility: String, confidence: String) {
    Log.d("combustibility", combustibility)
    Log.d("confidence", confidence)
    // add corner radius to picture
    val painter = rememberImagePainter(data = picture)
    //convert the file to a painter
    Card(
        modifier = Modifier
            .height(IntrinsicSize.Max)
            .padding(16.dp),
        elevation = 10.dp,
        backgroundColor = Color(0xFF2D2935),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
                .padding(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.2f)
            ) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    alignment = Alignment.CenterStart,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                )
            }
            Column() {
                Text(
                    when (combustibility) {
                        "Yes" -> "\uD83D\uDD25 Combustible"
                        "No" -> "Incombustible"
                        else -> "Unknown"
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.End,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    lineHeight = 24.sp,
                    letterSpacing = 0.5.sp,
                )
                Text(
                    "with $confidence% accuracy",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.End
                )
            }
        }
    }
}


// for number of images create a imagecard
// for each image create a row with a text and an image

@ExperimentalMaterial3Api
@Composable
fun ImageCardColumn() {

    val context = LocalContext.current
    val columnState = ScrollState(0)
    val folder = LocalContext.current.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    Log.d("folderPathName", folder.toString())
    val pictures = folder?.listFiles()

    Column(
        modifier = Modifier
            .padding(10.dp)
            .verticalScroll(state = columnState)
    )
    {
        if (pictures?.isNotEmpty() == true) {
            Log.d("pictures", pictures.toString())
            for (picture in pictures) {
                if (picture.toString().endsWith(".jpg")) {
                    if ((picture.nameWithoutExtension.endsWith("Yes")) or (picture.nameWithoutExtension.endsWith(
                            "No"
                        ))
                    ) {
                        val combustibility = picture.nameWithoutExtension.split("_").lastIndex()
                        val confidence = picture.nameWithoutExtension.split("_")[confidence.size - 1]
                        ImageCard(picture, combustibility, confidence)
                    }
                }
            }
        }
    }
}

@Composable
fun StoreImageData(key: String, value: Any?) {
    Log.d("imagekey", key)
    Log.d("imagevalue", value.toString())
}
