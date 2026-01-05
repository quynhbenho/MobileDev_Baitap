package com.example.week3payment.model

import com.example.week3payment.R

object GooglePay : PaymentMethod {
    override val id = "google_pay"
    override val name = "Google Pay"
    override val icon = R.drawable.ic_googlepay
}
