plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
  id("com.google.devtools.ksp")
}
android {
  namespace = "com.github.tera330.apps.chatgpt"
  compileSdk = 34
  defaultConfig {
    applicationId = "com.github.tera330.apps.chatgpt"
    minSdk = 24
    targetSdk = 34
    versionCode = 1
    versionName = "0.0.1" // X.Y.Z; X = Major, Y = minor, Z = Patch level
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }
  buildFeatures {
    compose = true
  }
  composeOptions {
    // https://developer.android.com/jetpack/androidx/releases/compose-kotlin
    kotlinCompilerExtensionVersion = "1.5.3"
  }
  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
  kotlinOptions {
    jvmTarget = JavaVersion.VERSION_11.toString()
  }
}
dependencies {
  // https://developer.android.com/jetpack/compose/setup?hl=ja#setup-compose
  implementation(platform("androidx.compose:compose-bom:2023.01.00"))
  androidTestImplementation(platform("androidx.compose:compose-bom:2023.01.00"))
  implementation("com.google.android.material:material:1.11.0")
  implementation("androidx.compose.material:material")
  implementation("androidx.compose.ui:ui-tooling-preview")
  debugImplementation("androidx.compose.ui:ui-tooling")
  androidTestImplementation("androidx.compose.ui:ui-test-junit4")
  debugImplementation("androidx.compose.ui:ui-test-manifest")
  implementation("androidx.compose.material:material-icons-core")
  implementation("androidx.compose.material:material-icons-extended")
  implementation("androidx.compose.material3:material3-window-size-class")
  implementation("androidx.activity:activity-compose:1.6.1")
  implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
  implementation("androidx.compose.runtime:runtime-livedata")
  implementation("androidx.compose.runtime:runtime-rxjava2")

  implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
  implementation("androidx.compose.ui:ui")
  implementation("androidx.compose.ui:ui-graphics")
  implementation("androidx.compose.ui:ui-tooling-preview")
  implementation("androidx.compose.material3:material3")

}
