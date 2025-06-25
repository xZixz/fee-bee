plugins {
    alias(libs.plugins.feebee.android.library)
    alias(libs.plugins.feebee.compose.library)
}

android {
    namespace = "com.cardes.ui"
}

dependencies {
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
