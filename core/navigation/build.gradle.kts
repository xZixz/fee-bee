plugins {
    alias(libs.plugins.feebee.android.library)
    alias(libs.plugins.feebee.compose.library)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.cardes.navigation"
}

dependencies {
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.runtime.android)
}
