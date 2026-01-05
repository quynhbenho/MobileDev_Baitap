package com.example.dataflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.dataflow.navigation.AppNavGraph
import com.example.dataflow.ui.theme.DataflowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DataflowTheme {
                val navController = rememberNavController()
                AppNavGraph(navController)
            }
        }
    }
}
