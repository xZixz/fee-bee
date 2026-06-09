plugins {
    alias(libs.plugins.feebee.android.feature)
}

android {
    namespace = "com.cardes.spendingdetail"
}

dependencies {
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
