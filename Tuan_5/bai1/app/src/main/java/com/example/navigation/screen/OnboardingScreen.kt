package com.example.navigation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.navigation.model.onboardingItems

@Composable
fun OnboardingScreen(navController: NavController) {

    var page by remember { mutableStateOf(0) }
    val item = onboardingItems[page]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        // ===== TOP BAR: DOTS + SKIP =====
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DotsIndicator(
                totalDots = onboardingItems.size,
                selectedIndex = page
            )

            TextButton(
                onClick = { page = onboardingItems.lastIndex }
            ) {
                Text("Skip")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // ===== IMAGE =====
        Image(
            painter = painterResource(id = item.image),
            contentDescription = null,
            modifier = Modifier
                .height(250.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // ===== TITLE =====
        Text(
            text = item.title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // ===== DESCRIPTION =====
        Text(
            text = item.description,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.weight(1f))

        // ===== BOTTOM BUTTONS =====
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (page > 0) {
                OutlinedButton(
                    onClick = { page-- }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Back")
                }
            } else {
                Spacer(modifier = Modifier.width(90.dp))
            }

            Button(
                onClick = {
                    if (page < onboardingItems.lastIndex) {
                        page++
                    } else {
                        // TODO: Navigate to Home screen nếu có
                    }
                }
            ) {
                Text(
                    if (page == onboardingItems.lastIndex)
                        "Get Started"
                    else
                        "Next"
                )
            }
        }
    }
}

// ===== DOT INDICATOR =====
@Composable
fun DotsIndicator(
    totalDots: Int,
    selectedIndex: Int
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(totalDots) { index ->
            Box(
                modifier = Modifier
                    .size(if (index == selectedIndex) 10.dp else 8.dp)
                    .background(
                        color = if (index == selectedIndex)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                        shape = CircleShape
                    )
            )
        }
    }
}
