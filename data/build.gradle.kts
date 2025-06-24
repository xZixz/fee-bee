plugins {
    alias(libs.plugins.feebee.android.library)
    alias(libs.plugins.feebee.inject)
    alias(libs.plugins.feebee.hilt)
    alias(libs.plugins.feebee.room)
}

android {
    namespace = "com.cardes.data"

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
}

dependencies {
    testImplementation(libs.junit)
    testImplementation(libs.google.truth)
    androidTestImplementation(libs.coroutines.test)
    androidTestImplementation(libs.google.truth)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(projects.domain)
    api(projects.core.common)
}
