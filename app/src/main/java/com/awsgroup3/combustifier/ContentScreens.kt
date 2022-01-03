package com.awsgroup3.combustifier

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.awsgroup3.combustifier.ui.theme.CombustifierTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi

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
                Column() {
                    ImageCard(null)
                }
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
fun ImageCard(picture: Any?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = 10.dp,
        backgroundColor = Color(0xFF2D2935)
    ) {
        Column(
            modifier = Modifier.padding(15.dp)
        )
        {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Hello")
                val image: Painter = painterResource(id = R.drawable.combustifierlogo)
                Image(painter = image, contentDescription = null)
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun ImageCardColumn() {

}