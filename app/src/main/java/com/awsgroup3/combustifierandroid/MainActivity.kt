package com.awsgroup3.combustifierandroid

//Imports
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.awsgroup3.combustifierandroid.ui.theme.CombustifierAndroidTheme

// Material 3 imports below


// MainActivity
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CombustifierAndroidTheme {
                HomeContent()
            }
        }
    }
}


// Homepage contents
@Composable
fun HomeContent() {
    Scaffold(
        content = { Text("BodyContent") },
        bottomBar = {BottomNavigationBar()})
}


// Navbar

@Composable
fun BottomNavigationBar() {
    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Measurement
    )
    BottomNavigation(
        backgroundColor = Color(0xFF404E30),
        contentColor = Color.White
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon),
                contentDescription = item.title) },
                label = { Text(text = item.title) },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.7f),
                alwaysShowLabel = true,
                selected = false,
                onClick = {
                    /* Add code later */
                }
            )
        }
    }
}


// Preview (not important)

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CombustifierAndroidTheme {
        BottomNavigationBar()
    }
}