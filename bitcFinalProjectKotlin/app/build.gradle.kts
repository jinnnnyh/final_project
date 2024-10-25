plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
}

android {
  namespace = "com.fullstack405.bitcfinalprojectkotlin"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.fullstack405.bitcfinalprojectkotlin"
    minSdk = 26
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = "1.8"
  }
  buildFeatures {
    viewBinding = true
  }
}

dependencies {

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.appcompat)
  implementation(libs.material)
  implementation(libs.androidx.activity)
  implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
  implementation(libs.play.services.mlkit.barcode.scanning)
    implementation(libs.cronet.embedded)
    implementation(libs.firebase.messaging)
    testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)

//  Glide
  implementation ("com.github.bumptech.glide:glide:4.15.1")
  annotationProcessor ("com.github.bumptech.glide:compiler:4.15.1")

//  Retrofit2
  implementation("com.squareup.retrofit2:retrofit:2.9.0")
  implementation("com.squareup.retrofit2:converter-gson:2.9.0")

// okhttp2
  implementation ("com.squareup.okhttp3:okhttp:4.10.0")
  implementation ("com.squareup.okhttp3:logging-interceptor:4.10.0")

//  fragment, viewpager2
  implementation ("androidx.viewpager2:viewpager2:1.0.0'")
  implementation ("androidx.fragment:fragment:1.5.0")

// qr
  implementation ("com.journeyapps:zxing-android-embedded:4.3.0")
  implementation("com.google.zxing:core:3.3.3")

  // firebase
  implementation("com.google.firebase:firebase-messaging-ktx:24.0.2")

}