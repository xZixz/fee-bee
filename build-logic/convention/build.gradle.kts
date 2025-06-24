plugins {
    `kotlin-dsl`
}

group = "com.cardes.feebee.buildconvention"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.hilt.gradlePlugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = libs.plugins.feebee.android.application
                .get()
                .pluginId
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = libs.plugins.feebee.android.library
                .get()
                .pluginId
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("kotlinLibrary") {
            id = libs.plugins.feebee.kotlin.library
                .get()
                .pluginId
            implementationClass = "KotlinLibraryConventionPlugin"
        }
        register("injectLibrary") {
            id = libs.plugins.feebee.inject
                .get()
                .pluginId
            implementationClass = "InjectLibraryConventionPlugin"
        }
        register("hiltLibrary") {
            id = libs.plugins.feebee.hilt
                .get()
                .pluginId
            implementationClass = "HiltConventionPlugin"
        }
    }
}
