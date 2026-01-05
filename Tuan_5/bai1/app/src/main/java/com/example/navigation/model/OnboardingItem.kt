package com.example.navigation.model

import androidx.annotation.DrawableRes
import com.example.navigation.R

data class OnboardingItem(
    val title: String,
    val description: String,
    @DrawableRes val image: Int
)

val onboardingItems = listOf(
    OnboardingItem(
        title = "Easy Time Management",
        description = "With management based on priority and daily tasks, it will give you convenience in managing and determining the tasks that must be done first",
        image = R.drawable.anh1
    ),
    OnboardingItem(
        title = "Increase Work Effectiveness",
        description = "Time management and the determination of more important tasks will give your job statistics better and always improve",
        image = R.drawable.anh2
    ),
    OnboardingItem(
        title = "Reminder Notification",
        description = "The advantage of this application is that it also provides reminders for you so you don't forget to keep doing your assignments well and according to the time you have set",
        image = R.drawable.anh3
    )
)
