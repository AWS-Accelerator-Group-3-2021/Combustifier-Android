package com.awsgroup3.combustifier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.awsgroup3.combustifier.ui.theme.CombustifierTheme
import com.awsgroup3.combustifier.ui.theme.Typography

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
                        icon = { if (stringResource(screen.resourceId) == "home") {
                            Icon(Icons.Filled.Home, contentDescription = null)
                        } else {
                            Icon(painterResource(id = R.drawable.baseline_straighten_24), contentDescription = null)
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
        NavHost(navController, startDestination = Screen.Home.route, Modifier.padding(innerPadding)) {
            composable(Screen.Home.route) { HomeScreen(navController) }
            composable(Screen.Measurement.route) { MeasurementScreen(navController) }
            composable(Screen.Camera.route) { CameraScreen(navController) }
        }
    }
}


@Composable
fun TopAppBar(pageName: String) {
    SmallTopAppBar(
        title = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp, 48.dp, 24.dp, 24.dp)
                ,
                text = pageName,
                fontFamily = Typography.titleLarge.fontFamily,
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
    )
}



@Composable
fun NewCheckButton(navController: NavController) {
    ExtendedFloatingActionButton(
        text = { Text("New Check")},
        icon = {Icon(Icons.Filled.AddCircle, contentDescription = null)},
        onClick = {
            navController.navigate("camera") {
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



/*fun BottomNavigationBar() {
    val navController = rememberNavController()
    var selectedIndex by remember { mutableStateOf(0) }
    NavigationBar () {
        NavigationBarItem(
            enabled = true,
            icon = { Icon(Icons.Filled.Home, contentDescription = null) },
            label = { Text("Home") },
            onClick = {
                navController.navigate("home") {
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
                selectedIndex = 0
            },
            selected = selectedIndex == 0
        )
        //Measurement
        NavigationBarItem(
            icon = {
                Icon(
                    painterResource(id = R.drawable.baseline_straighten_24),
                    contentDescription = null
                )
            },
            enabled = true,
            label = { Text("Measurement") },
            onClick = {navController.navigate("measurement") {
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
                selectedIndex = 1
            },
            selected = selectedIndex == 1
        )
    }
}
*/
