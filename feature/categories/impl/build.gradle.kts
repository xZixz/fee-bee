plugins {
    alias(libs.plugins.feebee.android.feature.impl)
}

android {
    namespace = "com.cardes.categories.impl"
}

dependencies {
    implementation(projects.feature.categories.api)
    implementation(projects.feature.editcategory.api)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
