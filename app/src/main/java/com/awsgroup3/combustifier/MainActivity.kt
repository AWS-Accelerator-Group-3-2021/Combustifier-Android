package com.awsgroup3.combustifier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
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
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) {
        Navigation(navController)
    }
}


@Composable
fun TopAppBar(pageName: String) {
    SmallTopAppBar(
        title = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp, 48.dp,24.dp,24.dp)
                ,
                text = pageName,
                fontFamily = Typography.titleLarge.fontFamily,
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
    )
}

@ExperimentalMaterial3Api
@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController, startDestination = NavigationItem.Home.route) {
        composable(NavigationItem.Home.route) {
            HomeScreen()
        }
        composable(NavigationItem.Measurement.route) {
            MeasurementScreen()
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar (){
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        //Home
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = null) },
            label = { Text("Home") },
            selected = currentRoute == "Home",
            onClick = {
                navController.navigate("Home") {
                    navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route) {
                            saveState = true
                        }
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        //Measurement
        NavigationBarItem(
            icon = {
                Icon(
                    painterResource(id = R.drawable.baseline_straighten_24),
                    contentDescription = null
                )
            },
            label = { Text("Measurement") },
            selected = currentRoute == "Measurement",
            onClick = {
                navController.navigate("Measurement") {
                    navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route) {
                            saveState = true
                        }
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CombustifierTheme {
        TopAppBar("Hello Preview")
    }
}