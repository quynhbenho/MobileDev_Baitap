package com.example.getapi.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.getapi.model.Task

@Composable
fun TaskItem(
    task: Task,
    onClick: () -> Unit
) {
    val bgColor = when (task.status) {
        "In Progress" -> Color(0xFFF2C6CC)
        "Pending" -> Color(0xFFE8F2C6)
        else -> Color(0xFFC6E6F2)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .background(bgColor, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        Row {
            Icon(
                Icons.Default.CheckBoxOutlineBlank,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(task.title, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    task.description ?: "",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Status: ${task.status}",
            style = MaterialTheme.typography.bodySmall
        )
    }
}
