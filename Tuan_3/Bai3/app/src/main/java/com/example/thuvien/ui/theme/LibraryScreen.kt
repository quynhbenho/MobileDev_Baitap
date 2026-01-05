package com.example.thuvien.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.thuvien.model.Book

// Enum quản lý các tab màn hình
enum class ScreenType {
    MANAGER, BOOK_LIST, STUDENTS
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen() {
    var currentScreen by remember { mutableStateOf(ScreenType.MANAGER) }

    // --- DỮ LIỆU ---
    val students = remember { listOf("Nguyen Nhu Quynh", "Tran Thi B", "Le Van C", "Pham Van D") }
    var currentStudentName by remember { mutableStateOf(students[0]) }

    val allBooks = remember {
        listOf(
            Book("1", "Sách 01: Nhập môn Java"),
            Book("2", "Sách 02: Lập trình Android"),
            Book("3", "Sách 03: Cấu trúc dữ liệu"),
            Book("4", "Sách 04: Cơ sở dữ liệu"),
            Book("5", "Sách 05: Trí tuệ nhân tạo")
        )
    }

    // Map lưu trữ sách đã mượn theo từng sinh viên
    val borrowedBooksMap = remember { mutableStateMapOf<String, Set<String>>() }

    // Lấy danh sách ID sách đã mượn của sinh viên hiện tại
    val currentSelectedIds = borrowedBooksMap[currentStudentName] ?: emptySet()

    // Dialog State
    var showStudentDialog by remember { mutableStateOf(false) }
    var showBookDialog by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = null) },
                    label = { Text("Quản lý") },
                    selected = currentScreen == ScreenType.MANAGER,
                    onClick = { currentScreen = ScreenType.MANAGER },
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = Color(0xFF0D47A1))
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.List, contentDescription = null) },
                    label = { Text("DS Sách") },
                    selected = currentScreen == ScreenType.BOOK_LIST,
                    onClick = { currentScreen = ScreenType.BOOK_LIST },
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = Color(0xFF0D47A1))
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Person, contentDescription = null) },
                    label = { Text("Sinh viên") },
                    selected = currentScreen == ScreenType.STUDENTS,
                    onClick = { currentScreen = ScreenType.STUDENTS },
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = Color(0xFF0D47A1))
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {

            when (currentScreen) {
                ScreenType.MANAGER -> ManagerTab(
                    currentName = currentStudentName,
                    allBooks = allBooks,
                    selectedBookIds = currentSelectedIds,
                    onOpenStudentDialog = { showStudentDialog = true },
                    onOpenBookDialog = { showBookDialog = true },
                    onRemoveBook = { bookId ->
                        val newSet = currentSelectedIds - bookId
                        borrowedBooksMap[currentStudentName] = newSet
                    }
                )
                // --- SỬA CHỖ NÀY: Truyền thêm selectedBookIds vào để kiểm tra trạng thái ---
                ScreenType.BOOK_LIST -> BookListTab(
                    books = allBooks,
                    borrowedIds = currentSelectedIds // Truyền danh sách đang mượn vào đây
                )
                ScreenType.STUDENTS -> StudentListTab(students)
            }

            // --- DIALOG CHỌN SINH VIÊN ---
            if (showStudentDialog) {
                Dialog(onDismissRequest = { showStudentDialog = false }) {
                    Card(
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Chọn Sinh Viên", fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.padding(bottom = 15.dp))
                            LazyColumn(modifier = Modifier.weight(1f)) {
                                items(students) { name ->
                                    Text(
                                        text = name,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                currentStudentName = name
                                                showStudentDialog = false
                                            }
                                            .padding(vertical = 15.dp),
                                        fontSize = 16.sp
                                    )
                                    Divider(color = Color.LightGray)
                                }
                            }
                            Button(
                                onClick = { showStudentDialog = false },
                                modifier = Modifier.align(Alignment.End),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                            ) { Text("Đóng") }
                        }
                    }
                }
            }

            // --- DIALOG CHỌN SÁCH ---
            if (showBookDialog) {
                Dialog(onDismissRequest = { showBookDialog = false }) {
                    Card(
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(500.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Chọn Sách Để Mượn", fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.padding(bottom = 15.dp))
                            LazyColumn(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                items(allBooks) { book ->
                                    val isSelected = currentSelectedIds.contains(book.id)

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(Color(0xFFF5F5F5), RoundedCornerShape(8.dp))
                                            .padding(10.dp)
                                            .clickable {
                                                val newSet = if (isSelected) {
                                                    currentSelectedIds - book.id
                                                } else {
                                                    currentSelectedIds + book.id
                                                }
                                                borrowedBooksMap[currentStudentName] = newSet
                                            },
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Checkbox(
                                            checked = isSelected,
                                            onCheckedChange = { isChecked ->
                                                val newSet = if (!isChecked) {
                                                    currentSelectedIds - book.id
                                                } else {
                                                    currentSelectedIds + book.id
                                                }
                                                borrowedBooksMap[currentStudentName] = newSet
                                            },
                                            colors = CheckboxDefaults.colors(checkedColor = Color(0xFFC2185B))
                                        )
                                        Text(book.name, modifier = Modifier.padding(start = 8.dp))
                                    }
                                }
                            }
                            Button(
                                onClick = { showBookDialog = false },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D47A1))
                            ) { Text("Hoàn tất chọn") }
                        }
                    }
                }
            }
        }
    }
}

// --- TAB 1: QUẢN LÝ ---
@Composable
fun ManagerTab(
    currentName: String,
    allBooks: List<Book>,
    selectedBookIds: Set<String>,
    onOpenStudentDialog: () -> Unit,
    onOpenBookDialog: () -> Unit,
    onRemoveBook: (String) -> Unit
) {
    val selectedBooksList = allBooks.filter { selectedBookIds.contains(it.id) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Hệ thống\nQuản lý Thư viện", fontSize = 22.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(25.dp))

        // Input Sinh viên
        Column(modifier = Modifier.fillMaxWidth()) {
            Text("Sinh viên", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(currentName, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    onClick = { onOpenStudentDialog() },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D47A1))
                ) { Text("Thay đổi") }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Danh sách sách ĐÃ MƯỢN
        Text("Danh sách sách", fontWeight = FontWeight.Bold, modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color(0xFFEEEEEE), RoundedCornerShape(10.dp))
                .padding(10.dp),
            contentAlignment = if (selectedBooksList.isEmpty()) Alignment.Center else Alignment.TopStart
        ) {
            if (selectedBooksList.isEmpty()) {
                Text(
                    text = "Bạn chưa mượn quyển sách nào\nNhấn 'Thêm' để bắt đầu hành trình đọc sách!",
                    textAlign = TextAlign.Center, color = Color.Gray, fontWeight = FontWeight.Bold
                )
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(selectedBooksList) { book ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White, RoundedCornerShape(8.dp))
                                .padding(15.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Filled.Done, contentDescription = null, tint = Color(0xFFC2185B))
                            Text(book.name, modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 10.dp), fontWeight = FontWeight.Medium)
                            Icon(
                                Icons.Filled.Close, contentDescription = "Remove", tint = Color.Gray,
                                modifier = Modifier.clickable { onRemoveBook(book.id) }
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // NÚT THÊM
        Button(
            onClick = { onOpenBookDialog() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D47A1))
        ) { Text("Thêm", fontSize = 18.sp, fontWeight = FontWeight.Bold) }
    }
}

// --- TAB 2: KHO SÁCH (ĐÃ SỬA LOGIC HIỂN THỊ) ---
@Composable
fun BookListTab(
    books: List<Book>,
    borrowedIds: Set<String> // Nhận vào danh sách ID đang mượn
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp)) {
        Text("Kho Sách", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0D47A1))

        Spacer(modifier = Modifier.height(15.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(books) { book ->
                // Kiểm tra xem ID sách này có trong danh sách đang mượn không
                val isBorrowed = borrowedIds.contains(book.id)

                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)), modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(15.dp)) {
                        Text(book.name, fontWeight = FontWeight.Bold)

                        // Hiển thị trạng thái dựa trên biến isBorrowed
                        if (isBorrowed) {
                            Text("Trạng thái: Đang mượn", color = Color(0xFFC2185B), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        } else {
                            Text("Trạng thái: Có sẵn", color = Color(0xFF00C853), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

// --- TAB 3: SINH VIÊN (CHỈ XEM) ---
@Composable
fun StudentListTab(students: List<String>) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp)) {
        Text("Danh Sách Sinh Viên", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0D47A1))
        Spacer(modifier = Modifier.height(15.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(students) { name ->
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF5F5F5), RoundedCornerShape(8.dp))
                    .padding(15.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Person, contentDescription = null, tint = Color.Gray)
                    Text(name, modifier = Modifier.padding(start = 15.dp), fontSize = 16.sp)
                }
            }
        }
    }
}
