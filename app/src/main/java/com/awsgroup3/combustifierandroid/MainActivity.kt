package com.awsgroup3.combustifierandroid
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.awsgroup3.combustifierandroid.ui.theme.CombustifierAndroidTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                HomeContent()
            }
        }
    }
}



@Composable
fun MyApp(content: @Composable () -> Unit) {
    CombustifierAndroidTheme() {
        Surface(color = MaterialTheme.colors.background) {
            content()
        }
    }
}

@Composable
fun HomeContent() {
    BottomNavigationBar()
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp {
        BottomNavigationBar()
    }
}


@Composable
fun BottomNavigationBar() {
    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Measurement
    )
    BottomNavigation(
        backgroundColor = colorResource(id = R.color.design_default_color_primary),
        contentColor = Color.White
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                label = { Text(text = item.title) },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.4f),
                alwaysShowLabel = true,
                selected = false,
                onClick = {
                    /* Add code later */
                }
            )
        }
    }
}