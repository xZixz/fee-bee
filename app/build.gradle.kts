plugins {
    alias(libs.plugins.feebee.android.application)
    alias(libs.plugins.feebee.compose.application)
    alias(libs.plugins.feebee.hilt)
    // TODO: Remove this after refactoring the bottom navigation
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.cardes.feebee"

    defaultConfig {
        applicationId = "com.cardes.feebee"
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    flavorDimensions += "version"

    productFlavors {
        create("dev") {
            dimension = "version"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
            resValue("string", "app_name", "FeeBee-dev")
        }
        create("prod") {
            dimension = "version"
            applicationIdSuffix = ".prod"
            versionNameSuffix = "-prod"
            resValue("string", "app_name", "FeeBee")
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewModel.compose)
    implementation(libs.com.squareup.javapoet)

    // Vico
    implementation(libs.vico.core)
    implementation(libs.vico.compose)
    implementation(libs.vico.compose.m2)
    implementation(libs.vico.compose.m3)
    implementation(libs.vico.views)

    // Number Picker
    implementation(libs.numberPicker)

    // Pie Chart
    implementation(libs.composeChart)

    testImplementation(libs.junit)
    testImplementation(libs.google.truth)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Modules
    implementation(projects.core.domain)
    implementation(projects.core.data)

    implementation(projects.core.designsystem)
    implementation(projects.core.ui)
    implementation(projects.core.common)
    implementation(projects.feature.spendings)
    implementation(projects.feature.analytics)
    implementation(projects.feature.categories)
    implementation(projects.feature.editcategory)
    implementation(projects.feature.spendingdetail)
}
