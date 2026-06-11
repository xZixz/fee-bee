plugins {
    alias(libs.plugins.feebee.android.feature.impl)
}

android {
    namespace = "com.cardes.editcategory.impl"
}

dependencies {
    implementation(projects.feature.editcategory.api)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
