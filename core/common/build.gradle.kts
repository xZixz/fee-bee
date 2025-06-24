plugins {
    alias(libs.plugins.feebee.android.library)
    alias(libs.plugins.feebee.inject)
    alias(libs.plugins.feebee.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.cardes.core.common"
}

dependencies {
    implementation(libs.com.squareup.javapoet)
}
