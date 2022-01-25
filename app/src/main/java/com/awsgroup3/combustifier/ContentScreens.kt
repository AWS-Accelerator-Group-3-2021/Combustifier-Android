package com.awsgroup3.combustifier

import android.content.Intent
import android.os.Environment
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
    CombustifierTheme {
        //wait a few seconds before launching the activity
        ContextCompat.startActivity(LocalContext.current, intent, null)
    }
}


@ExperimentalMaterial3Api
@Composable
fun ImageCard(picture: File) {
    val painter = rememberImagePainter(picture)
    Log.d("imagepainter", picture.absolutePath)
    //convert the file to a painter
        Image(
            painter,
            null
        )
    }


// for number of images create a imagecard
// for each image create a row with a text and an image

@ExperimentalMaterial3Api
@Composable
fun ImageCardColumn() {
    val columnState = ScrollState(0)
    val folder =
        File(LocalContext.current.getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString())
    Log.d("folderPathName", folder.toString())
    //return uri of all images in folder
    val pictures = folder.listFiles()
    // filter only jpg
    val jpgPictures = pictures.filter { it.name.endsWith(".jpg") }
    Log.d("pictures", pictures.toString())
    Column(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxHeight()
            .verticalScroll(state = columnState, enabled = true)
    )
    {
        for (pic in jpgPictures) {
            Log.d("file", pic.toString())
            ImageCard(pic)
        }
    }
}

