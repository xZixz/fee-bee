plugins {
    alias(libs.plugins.feebee.android.feature.impl)
}

android {
    namespace = "com.cardes.spendingdetail.impl"
}

dependencies {
    implementation(projects.feature.spendingdetail.api)
    implementation(projects.feature.editspending.api)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
