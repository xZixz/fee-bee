@file:Suppress("UnstableApiUsage")

package com.cardes.buildconvention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

fun Project.configureCompose(commonExtension: CommonExtension<*, *, *, *, *, *>) {
    dependencies {
        val bom = libs.findLibrary("androidx.compose.bom").get()
        implementation(platform(bom))
        implementation(libs.findLibrary("androidx.material3").get())
        implementation(libs.findLibrary("androidx.ui").get())
        implementation(libs.findLibrary("androidx.ui.graphics").get())
        implementation(libs.findLibrary("androidx.ui.tooling.preview").get())
        implementation(libs.findLibrary("androidx.material.material.icons").get())
        implementation(libs.findLibrary("androidx.material.material.icons.extended").get())
        implementation(libs.findLibrary("androidx.activity.compose").get())
        implementation(libs.findLibrary("androidx.hilt.navigation.compose").get())
        "debugImplementation"(libs.findLibrary("androidx.ui.tooling").get())
        "debugImplementation"(libs.findLibrary("androidx.ui.test.manifest").get())
        "androidTestImplementation"(platform(bom))
        "androidTestImplementation"(libs.findLibrary("androidx.ui.test.junit4").get())
    }

    commonExtension.apply {
        buildFeatures {
            compose = true
        }
        testOptions {
            unitTests {
                isIncludeAndroidResources = true
            }
        }
    }

    extensions.configure<ComposeCompilerGradlePluginExtension> {
        fun Provider<String>.onlyIfTrue() = flatMap { provider { it.takeIf(String::toBoolean) } }

        fun Provider<*>.relativeToRootProject(dir: String) =
            map {
                isolated.rootProject.projectDirectory
                    .dir("build")
                    .dir(projectDir.toRelativeString(rootDir))
            }.map { it.dir(dir) }

        project.providers
            .gradleProperty("enableComposeCompilerMetrics")
            .onlyIfTrue()
            .relativeToRootProject("compose-metrics")
            .let(metricsDestination::set)

        project.providers
            .gradleProperty("enableComposeCompilerReports")
            .onlyIfTrue()
            .relativeToRootProject("compose-reports")
            .let(reportsDestination::set)

        stabilityConfigurationFiles
            .add(isolated.rootProject.projectDirectory.file("compose_compiler_config.conf"))
    }
}
