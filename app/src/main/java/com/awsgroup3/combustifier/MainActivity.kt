package com.awsgroup3.combustifier

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.icu.text.DateFormat.getDateTimeInstance
import android.os.Bundle
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.camera.core.impl.utils.ContextUtil.getApplicationContext
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import coil.annotation.ExperimentalCoilApi
import com.awsgroup3.combustifier.ui.theme.CombustifierTheme
import com.awsgroup3.combustifier.ui.theme.Typography
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

import java.text.SimpleDateFormat
import java.util.*



class MainActivity : ComponentActivity() {
    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CombustifierTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colorScheme.background) {
                    MainScreen()
                }
            }
        }
    }
}


@ExperimentalMaterial3Api
@Composable
fun MainScreen() {
    val items = listOf(
        Screen.Home,
        Screen.Measurement,
    )
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar() {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = {
                            if (stringResource(screen.resourceId) == "home") {
                                Icon(Icons.Filled.Home, contentDescription = null)
                            } else {
                                Icon(
                                    painterResource(id = R.drawable.baseline_straighten_24),
                                    contentDescription = null
                                )
                            }
                        },
                        label = { Text(stringResource(screen.resourceId)) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.Home.route,
            Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) { HomeScreen(navController) }
            composable(Screen.Measurement.route) { MeasurementScreen(navController) }
        }
    }
}


@Composable
fun TopAppBar(pageName: String) {
    SmallTopAppBar(
        title = {
            Text(
                modifier = Modifier
                    .padding(24.dp, 48.dp, 24.dp, 24.dp),
                text = pageName,
                fontFamily = Typography.titleLarge.fontFamily,
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
    )
}


@Composable
@OptIn(
    ExperimentalCoilApi::class,
    ExperimentalPermissionsApi::class, androidx.compose.material3.ExperimentalMaterial3Api::class
)
fun NewCheckButton() {
    val datetime = getDateTimeInstance()
    val result = remember { mutableStateOf<Bitmap?>(null) }
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicturePreview(
        )
    ) {
        result.value = it
    }
    ExtendedFloatingActionButton(
        modifier = Modifier
            .padding(16.dp),
        text = { Text("New Check") },
        icon = { Icon(Icons.Filled.AddCircle, contentDescription = null) },
        onClick = {

            launcher.launch()

        }
    )
    result.value?.let { image ->
        val root = Environment.getExternalStorageDirectory().absolutePath
        val imgFile = File(root, "$datetime;_combustifier.jpg")
        image.compress(Bitmap.CompressFormat.JPEG, 90, FileOutputStream(imgFile))

    }
}
