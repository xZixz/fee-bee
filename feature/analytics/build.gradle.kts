plugins {
    alias(libs.plugins.feebee.android.feature)
}

android {
    namespace = "com.cardes.analytics"
}

dependencies {
    // Vico
    implementation(libs.vico.core)
    implementation(libs.vico.compose)
    implementation(libs.vico.compose.m2)
    implementation(libs.vico.compose.m3)
    implementation(libs.vico.views)

    // Number Picker
    implementation(libs.numberPicker)

    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
