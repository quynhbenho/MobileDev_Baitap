package com.example.dataflow.navigation

object Routes {
    // Định nghĩa các đường dẫn có kèm tham số
    const val FORGET = "forget_password_screen"
    const val VERIFY = "verify_code_screen/{email}"
    const val RESET = "reset_password_screen/{email}/{code}"
    const val CONFIRM = "confirm_screen/{email}/{code}/{password}"

    // Hàm hỗ trợ tạo đường dẫn nhanh
    fun getVerifyRoute(email: String) = "verify_code_screen/$email"
    fun getResetRoute(email: String, code: String) = "reset_password_screen/$email/$code"
    fun getConfirmRoute(email: String, code: String, pass: String) = "confirm_screen/$email/$code/$pass"
}
