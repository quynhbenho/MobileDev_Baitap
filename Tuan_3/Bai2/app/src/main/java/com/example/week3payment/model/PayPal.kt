package com.example.week3payment.model

import com.example.week3payment.R

object PayPal : PaymentMethod {
    override val id = "paypal"
    override val name = "PayPal"
    override val icon = R.drawable.ic_paypal
}
