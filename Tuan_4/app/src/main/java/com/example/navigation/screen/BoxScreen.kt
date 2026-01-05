package com.example.navigation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoxScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Box Layout", color = Color(0xFF2196F3), fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = Color(0xFF2196F3))
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Box allows stacking elements", modifier = Modifier.padding(bottom = 20.dp))

            // Box ví dụ: Xếp chồng 3 ô vuông
            Box(
                contentAlignment = Alignment.BottomEnd, // Căn phần tử con xuống góc phải dưới
                modifier = Modifier
                    .size(200.dp)
                    .background(Color.LightGray)
            ) {
                // Lớp dưới cùng
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .background(Color.Red)
                        .align(Alignment.TopStart) // Góc trái trên
                )

                // Lớp giữa
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Green)
                        .align(Alignment.Center) // Chính giữa
                )

                // Lớp trên cùng
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color.Blue)
                    // Mặc định ăn theo contentAlignment của Box cha (Góc phải dưới)
                )
            }
        }
    }
}
