plugins {
    alias(libs.plugins.feebee.android.library)
    alias(libs.plugins.feebee.nav3)
}

android {
    namespace = "com.cardes.spendings.api"
}

dependencies {
    implementation(projects.core.navigation)
}
