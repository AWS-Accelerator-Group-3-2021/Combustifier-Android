package com.awsgroup3.combustifier

import android.content.Intent
import android.os.Environment
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
fun ImageCard(picture: File) {
    val painter = rememberImagePainter(picture)
    //convert the file to a painter
    Card(
        modifier = Modifier
            .height(IntrinsicSize.Max)
            .padding(16.dp),
        elevation = 10.dp,
        backgroundColor = Color(0xFF2D2935)
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
            Image(painter = painter, contentDescription = null, alignment = Alignment.CenterStart, contentScale = ContentScale.Crop)}
            Text("combustible",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.End
            )
        }

    }
}


// for number of images create a imagecard
// for each image create a row with a text and an image

@ExperimentalMaterial3Api
@Composable
fun ImageCardColumn() {
    val columnState = ScrollState(0)
    val folder = LocalContext.current.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    Log.d("folderPathName", folder.toString())
    val pictures = folder?.listFiles()

    Column(
        modifier = Modifier
            .padding(1.dp)
            .verticalScroll(state = columnState)
    )
    {
        if (pictures?.isNotEmpty() == true) {
            Log.d("pictures", pictures.toString())
            for (picture in pictures) {
                if (picture.toString().endsWith(".jpg")) {
                    ImageCard(picture)
                }
            }
        }
        Log.d("pictures", "empty")
    }
}

