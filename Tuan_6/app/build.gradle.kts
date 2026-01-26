plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.flowlogin"
    compileSdk {
        version = release(36)
    }


    defaultConfig {
        applicationId = "com.example.flowlogin"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    // Core + Compose
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation("androidx.compose.material:material-icons-extended:1.6.0")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // --- FIREBASE ---
    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:34.7.0"))

    // Firebase Authentication
    implementation("com.google.firebase:firebase-auth")

    // ▼▼▼ CÁC TIỆN ÍCH FIREBASE ĐƯỢC THÊM VÀO ĐÂY ▼▼▼
    implementation("com.google.firebase:firebase-firestore") // Cho Cloud Firestore
    implementation("com.google.firebase:firebase-storage")   // Cho Firebase Storage

    // Google Sign-In "cổ điển"
    implementation("com.google.android.gms:play-services-auth:21.1.1")

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}

