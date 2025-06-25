plugins {
    alias(libs.plugins.feebee.android.library)
    alias(libs.plugins.feebee.compose.library)
}

android {
    namespace = "com.cardes.designsystem"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
