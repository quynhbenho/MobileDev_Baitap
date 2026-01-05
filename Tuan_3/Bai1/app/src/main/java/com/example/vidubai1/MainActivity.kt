package com.example.vidubai1

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

// Data Class giữ nguyên
data class Student(
    val id: String,
    val name: String,
    val gpa: Double?
)

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Ẩn thanh ActionBar (Cái thanh đen đen phía trên) đi cho thoáng
        supportActionBar?.hide()

        // --- DỰNG GIAO DIỆN MỚI ---

        // ScrollView bao ngoài để tránh bị lỗi khi bàn phím hiện lên
        val scroll = ScrollView(this).apply {
            isFillViewport = true
            setBackgroundColor(Color.parseColor("#F0F2F5")) // Màu nền xám xanh nhạt hiện đại
        }

        // Layout chính căn giữa màn hình
        val container = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER // Căn giữa tất cả mọi thứ theo cả chiều dọc và ngang
            setPadding(60, 60, 60, 60)
        }
        scroll.addView(container)

        // Hàm hỗ trợ tạo Background bo tròn cho ô nhập (Đỡ phải viết XML)
        fun createRoundedBackground(color: Int, strokeColor: Int): GradientDrawable {
            val drawable = GradientDrawable()
            drawable.shape = GradientDrawable.RECTANGLE
            drawable.cornerRadius = 30f // Bo góc
            drawable.setColor(color)
            drawable.setStroke(2, strokeColor) // Viền mỏng
            return drawable
        }

        // Ô nhập Mã SV
        val edtId = EditText(this).apply {
            hint = "Nhập Mã Sinh Viên (Bắt buộc)"
            background = createRoundedBackground(Color.WHITE, Color.LTGRAY)
            setPadding(40, 40, 40, 40)
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply { setMargins(0, 0, 0, 30) } // Cách dưới 30px
        }

        // Ô nhập Tên
        val edtName = EditText(this).apply {
            hint = "Nhập Họ Tên (Bắt buộc)"
            background = createRoundedBackground(Color.WHITE, Color.LTGRAY)
            setPadding(40, 40, 40, 40)
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply { setMargins(0, 0, 0, 30) }
        }

        // Ô nhập Điểm
        val edtGpa = EditText(this).apply {
            hint = "Nhập Điểm GPA (Có thể trống)"
            inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            background = createRoundedBackground(Color.WHITE, Color.LTGRAY)
            setPadding(40, 40, 40, 40)
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply { setMargins(0, 0, 0, 50) } // Cách nút bấm xa hơn chút
        }

        // Nút Lưu (Gradient xanh đẹp mắt)
        val btnSave = Button(this).apply {
            text = "LƯU THÔNG TIN"
            setTextColor(Color.WHITE)
            textSize = 16f
            // Tạo nền bo tròn màu xanh
            background = GradientDrawable().apply {
                cornerRadius = 30f
                setColor(Color.parseColor("#1976D2")) // Màu xanh dương đậm
            }
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }

        // Ô hiển thị kết quả (Ẩn đi ban đầu, khi nào có kết quả mới hiện)
        val txtResult = TextView(this).apply {
            visibility = View.GONE // Ẩn đi
            textSize = 16f
            setPadding(40, 40, 40, 40)
            setTextColor(Color.DKGRAY)
            // Nền màu trắng hơi trong suốt
            background = createRoundedBackground(Color.WHITE, Color.TRANSPARENT)

            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply { setMargins(0, 50, 0, 0) }

            elevation = 10f
        }

        // Thêm các View vào màn hình
        container.addView(edtId)
        container.addView(edtName)
        container.addView(edtGpa)
        container.addView(btnSave)
        container.addView(txtResult)

        setContentView(scroll)

        // --- XỬ LÝ LOGIC ---

        btnSave.setOnClickListener {
            val idInput = edtId.text.toString().trim()
            val nameInput = edtName.text.toString().trim()
            val gpaInput = edtGpa.text.toString().trim()

            var hasError = false

            // Validate
            if (idInput.isEmpty()) {
                edtId.error = "Thiếu mã SV!"
                hasError = true
            }
            if (nameInput.isEmpty()) {
                edtName.error = "Thiếu tên!"
                hasError = true
            }

            if (hasError) return@setOnClickListener

            // Xử lý Nullable
            val finalGpa: Double? = if (gpaInput.isEmpty()) null else gpaInput.toDoubleOrNull()

            val student = Student(idInput, nameInput, finalGpa)

            // Hiển thị kết quả
            val gpaText = student.gpa?.toString() ?: "Null (Chưa có)"

            // Logic đánh giá bằng .let
            var danhGia = "Chưa xếp loại"
            student.gpa?.let {
                danhGia = if(it >= 5.0) "Đạt yêu cầu" else "Cần cố gắng"
            }

            txtResult.text = """
                KẾT QUẢ XỬ LÝ
                --------------------------
                Mã SV:   ${student.id}
                Họ Tên:  ${student.name}
                Điểm:    $gpaText
                --------------------------
                Đánh giá: $danhGia
            """.trimIndent()

            txtResult.visibility = View.VISIBLE // Hiện bảng kết quả lên
        }
    }
}
