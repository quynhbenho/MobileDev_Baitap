package com.example.flowlogin.profile

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.flowlogin.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.io.path.exists

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()
    val storage = FirebaseStorage.getInstance()

    // State để lưu trữ thông tin người dùng
    var displayName by remember { mutableStateOf(user?.displayName ?: "") }
    var photoUrl by remember { mutableStateOf(user?.photoUrl) }
    var dateOfBirth by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }

    // Đọc ngày sinh từ Firestore khi màn hình được tạo
    LaunchedEffect(user) {
        if (user != null) {
            try {
                val document = db.collection("users").document(user.uid).get().await()
                dateOfBirth = if (document.exists()) document.getString("dateOfBirth") ?: "" else ""
            } catch (e: Exception) {
                dateOfBirth = "" // Nếu lỗi thì để trống
                Toast.makeText(context, "Lỗi tải dữ liệu: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                isLoading = false
            }
        } else {
            isLoading = false
        }
    }

    // Launcher để chọn ảnh từ thư viện
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val storageRef = storage.reference.child("avatars/${user?.uid}")
            storageRef.putFile(it)
                .continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let { throw it }
                    }
                    storageRef.downloadUrl
                }
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                        photoUrl = downloadUri
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setPhotoUri(downloadUri)
                            .build()
                        user?.updateProfile(profileUpdates)
                        Toast.makeText(context, "Cập nhật ảnh thành công!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Upload ảnh thất bại!", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Profile", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()), // Thêm thanh cuộn
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isLoading) {
                // Hiển thị vòng xoay trong khi tải dữ liệu
                CircularProgressIndicator(modifier = Modifier.padding(vertical = 50.dp))
            } else {
                Spacer(modifier = Modifier.height(24.dp))
                // Avatar
                Box(contentAlignment = Alignment.BottomEnd) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = photoUrl,
                            placeholder = painterResource(R.drawable.uth_logo) // Ảnh chờ
                        ),
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .clickable { imagePickerLauncher.launch("image/*") }, // Nhấn để chọn ảnh
                        contentScale = ContentScale.Crop
                    )
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = "Change Photo",
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .padding(6.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Tên
                OutlinedTextField(
                    value = displayName,
                    onValueChange = { displayName = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Email
                OutlinedTextField(
                    value = user?.email ?: "N/A",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Ngày sinh
                OutlinedTextField(
                    value = dateOfBirth,
                    onValueChange = { dateOfBirth = it },
                    label = { Text("Date of Birth") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Nút Save
                Button(
                    onClick = {
                        if (user != null) {
                            coroutineScope.launch {
                                try {
                                    // 1. Cập nhật tên trong Firebase Auth
                                    val profileUpdates = UserProfileChangeRequest.Builder()
                                        .setDisplayName(displayName)
                                        .build()
                                    user.updateProfile(profileUpdates).await()

                                    // 2. Lưu ngày sinh vào Firestore
                                    val userDoc = mapOf("dateOfBirth" to dateOfBirth)
                                    db.collection("users").document(user.uid).set(userDoc).await()

                                    Toast.makeText(context, "Đã lưu thông tin!", Toast.LENGTH_SHORT).show()
                                } catch (e: Exception) {
                                    Toast.makeText(context, "Lỗi khi lưu: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save Profile")
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Nút Back/Sign Out
                Button(
                    onClick = {
                        coroutineScope.launch {
                            auth.signOut()
                            val webClientId = context.getString(R.string.default_web_client_id)
                            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestIdToken(webClientId)
                                .requestEmail()
                                .build()
                            val googleSignInClient = GoogleSignIn.getClient(context, gso)

                            googleSignInClient.signOut().addOnCompleteListener {
                                navController.navigate("login") {
                                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                                    launchSingleTop = true
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1877F2))
                ) {
                    Text("Back & Sign Out", color = Color.White, fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
