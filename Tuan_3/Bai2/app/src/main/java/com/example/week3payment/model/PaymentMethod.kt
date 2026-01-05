package com.example.week3payment.model

import androidx.annotation.DrawableRes

interface PaymentMethod {
    val id: String
    val name: String

    @get:DrawableRes
    val icon: Int
}
