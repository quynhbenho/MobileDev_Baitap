package com.example.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.navigation.compose.rememberNavController
import com.example.navigation.navigation.AppNavGraph
import com.example.navigation.ui.theme.NavigationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(null)
        setContent {
            NavigationTheme {
                Surface {
                    val navController = rememberNavController()
                    AppNavGraph(navController)
                }
            }
        }
    }
}
