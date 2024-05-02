import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.mvi_chatgpt"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mvi_chatgpt"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        val key = "OPENAI_API_KEY"
        val value = gradleLocalProperties(rootDir, providers).getProperty(key)
        buildConfigField("String", key, requireNotNull(value))
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
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // 컴포즈 뷰모델
    implementation (libs.androidx.lifecycle.viewmodel.compose)

    // Navigation Compose
    implementation (libs.androidx.navigation.compose)

    // Hilt
    implementation (libs.hilt.android)
    ksp (libs.hilt.compiler)

    // Hilt Compose
    implementation (libs.androidx.hilt.navigation.compose)
    implementation (libs.kotlin.reflect)

    // Retrofit (proguard rules 추가)
    implementation (libs.retrofit)
    implementation (libs.converter.moshi)

    // Moshi (proguard rules 추가 (DTO Enum))
    implementation (libs.moshi.kotlin)
    ksp (libs.moshi.kotlin.codegen)

    // Okhttp
    implementation (libs.okhttp)
    implementation (libs.logging.interceptor)

    // chat bot
    // import Kotlin API client BOM
    implementation (platform("com.aallam.openai:openai-client-bom:3.7.1"))
    implementation ("com.aallam.openai:openai-client")
    runtimeOnly ("io.ktor:ktor-client-okhttp")

    // API 24 이상에서만 지원하는 Java8 라이브러리 대체
    coreLibraryDesugaring (libs.desugar.jdk.libs)

    // compose - constraint layout
    implementation (libs.androidx.constraintlayout.compose)

    // lottie
    implementation (libs.lottie.compose)
}