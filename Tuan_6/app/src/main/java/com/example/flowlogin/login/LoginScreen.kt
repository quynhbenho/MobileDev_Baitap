package com.example.flowlogin.login

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.flowlogin.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

// Hàm lấy GoogleSignInClient
fun getGoogleSignInClient(activity: Activity): GoogleSignInClient {
    val webClientId = activity.getString(R.string.default_web_client_id)
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(webClientId) // Yêu cầu idToken
        .requestEmail()
        .build()
    return GoogleSignIn.getClient(activity, gso)
}

@Composable
fun LoginScreen(navController: NavController) {

    val context = LocalContext.current as Activity
    val auth = FirebaseAuth.getInstance()

    // Kiểm tra xem người dùng đã đăng nhập chưa
    LaunchedEffect(Unit) {
        if (auth.currentUser != null) {
            navController.navigate("profile") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    // === PHẦN THAY ĐỔI LỚN NHẤT ===
    // Tạo một launcher để nhận kết quả từ màn hình đăng nhập Google
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // Kết quả trả về ở đây
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                // Đăng nhập Google thành công, lấy idToken
                val account = task.getResult(ApiException::class.java)!!
                Log.d("GoogleSignIn-Classic", "firebaseAuthWithGoogle:" + account.id)

                val firebaseCredential = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(firebaseCredential)
                    .addOnCompleteListener(context) { task ->
                        if (task.isSuccessful) {
                            // Đăng nhập Firebase thành công
                            Toast.makeText(context, "Đăng nhập Firebase thành công!", Toast.LENGTH_SHORT).show()
                            navController.navigate("profile") {
                                popUpTo("login") { inclusive = true }
                            }
                        } else {
                            // Đăng nhập Firebase thất bại
                            Log.w("GoogleSignIn-Classic", "signInWithCredential;failure", task.exception)
                            Toast.makeText(context, "Lỗi đăng nhập Firebase.", Toast.LENGTH_SHORT).show()
                        }
                    }
            } catch (e: ApiException) {
                // Đăng nhập Google thất bại
                Log.w("GoogleSignIn-Classic", "Google sign in failed", e)
                Toast.makeText(context, "Lỗi đăng nhập Google: ${e.statusCode}", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Đã hủy đăng nhập.", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.uth_logo),
            contentDescription = "UTH Logo",
            modifier = Modifier.size(120.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                // Khi nhấn nút, gọi màn hình đăng nhập của Google
                val googleSignInClient = getGoogleSignInClient(context)
                launcher.launch(googleSignInClient.signInIntent)
            }
        ) {
            Text("SIGN IN WITH GOOGLE")
        }
    }
}
