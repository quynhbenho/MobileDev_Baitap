package com.example.week3payment.model

import com.example.week3payment.R

object ApplePay : PaymentMethod {
    override val id = "apple_pay"
    override val name = "Apple Pay"
    override val icon = R.drawable.ic_applepay
}
