package com.awsgroup3.combustifier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.awsgroup3.combustifier.ui.theme.CombustifierTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.awsgroup3.combustifier.ui.theme.Typography

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CombustifierTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colorScheme.background) {
                    HomeScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Scaffold(
        topBar = { TopAppBar("Home") },
        bottomBar = { BottomNavigationBar() },
        content = {})
}

@Composable
fun TopAppBar(pageName: String) {
    SmallTopAppBar(
        title = {
            Text(
                text = pageName,
                fontFamily = Typography.titleLarge.fontFamily,
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold

            )
        }
    )
}

@Composable
fun BottomNavigationBar() {
    var selectedItem by remember { mutableStateOf(0) }
    NavigationBar {
        //Home
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = null) },
            label = { Text("Home") },
            selected = selectedItem == 0,
            onClick = { selectedItem = 0 }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painterResource(id = R.drawable.baseline_straighten_24),
                    contentDescription = null
                )
            },
            label = { Text("Measurement") },
            selected = selectedItem == 1,
            onClick = { selectedItem = 1 }
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