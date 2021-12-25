package com.awsgroup3.combustifier

import android.content.Intent
import android.graphics.Bitmap
import android.icu.text.DateFormat.getDateTimeInstance
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.awsgroup3.combustifier.ui.theme.CombustifierTheme
import com.awsgroup3.combustifier.ui.theme.Typography
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.delay
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


class MainActivity : ComponentActivity() {
    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CombustifierTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colorScheme.background) {
                    ScreenInitializer()
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenInitializer() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "splash_screen"
    ) {
        composable("splash_screen") {
            SplashScreen(navController = navController)
        }
        // Main Screen
        composable("main_screen") {
            MainScreen()
        }
    }
}

@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }

    // AnimationEffect
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                })
        )
        delay(3000L)
        navController.navigate("main_screen")
    }

    // Image
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.combustifierlogo),
            contentDescription = "Combustifier",
            modifier = Modifier.scale(scale.value)
        )
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
    val context = LocalContext.current
    val datetime = getDateTimeInstance()
    val result = remember { mutableStateOf<Bitmap?>(null) }
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicturePreview(

        )
    ){
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

    lateinit var currentPhotoPath: String

    @Throws(IOException::class)
    fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "combustifier_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    result.value?.let { image ->
        val f = createImageFile()
        val os = FileOutputStream(f)
        image.compress(Bitmap.CompressFormat.JPEG, 100, os)
        os.flush()
        os.close()
        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val contentUri = Uri.fromFile(f)
        mediaScanIntent.data = contentUri
        context.sendBroadcast(mediaScanIntent)
        val intent = Intent(context, SendImageActivity::class.java)
        intent.putExtra("imageBitmap", image)
        Log.d("contenturi", contentUri.toString())
        startActivity(context, intent, null)
        
    }
}

