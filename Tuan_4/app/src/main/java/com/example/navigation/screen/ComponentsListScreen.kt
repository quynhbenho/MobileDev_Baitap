package com.example.navigation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ComponentsListScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            "UI Components List",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2196F3)
        )

        // --- NHÓM 1: DISPLAY ---
        GroupHeader("Display Elements", Icons.Default.Home)

        ComponentItem("Text Styling", "Rich text, bold, italic...", Color(0xFFE3F2FD)) {
            navController.navigate("text")
        }
        ComponentItem("ImageView", "Load URL & Local images", Color(0xFFE3F2FD)) {
            navController.navigate("image")
        }

        // --- NHÓM 2: INPUTS ---
        GroupHeader("Form Inputs", Icons.Default.Person)

        ComponentItem("TextField", "Simple & Outlined inputs", Color(0xFFFFF3E0)) {
            navController.navigate("textfield")
        }
        ComponentItem("Buttons", "Filled, Outlined, Text buttons", Color(0xFFFFF3E0)) {
            navController.navigate("buttons")
        }

        // --- NHÓM 3: LAYOUTS ---
        GroupHeader("Layout Containers", Icons.Default.Add)

        ComponentItem("Row & Column", "Linear layouts", Color(0xFFE8F5E9)) {
            navController.navigate("row")
        }
        ComponentItem("Box", "Z-index stacking", Color(0xFFE8F5E9)) {
            navController.navigate("box")
        }

        // --- NHÓM 4: CONTROLS ---
        GroupHeader("Interactive Controls", Icons.Default.Settings)

        ComponentItem("Switch & Checkbox", "Toggles and selections", Color(0xFFF3E5F5)) {
            navController.navigate("switch_checkbox")
        }
        ComponentItem("Slider", "Value selection range", Color(0xFFF3E5F5)) {
            navController.navigate("slider")
        }

        // Khoảng trắng dưới cùng để không bị sát đáy màn hình
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
fun GroupHeader(title: String, icon: ImageVector) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )
    }
}

@Composable
fun ComponentItem(title: String, desc: String, bgColor: Color, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(bgColor)
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = desc,
                fontSize = 13.sp,
                color = Color.Gray
            )
        }
        Text(">", fontSize = 18.sp, color = Color.Gray)
    }
}
