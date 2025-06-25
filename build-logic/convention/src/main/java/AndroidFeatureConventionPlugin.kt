import com.cardes.buildconvention.implementation
import com.cardes.buildconvention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "feebee.android.library")
            apply(plugin = "feebee.hilt")
            apply(plugin = "feebee.compose.library")
            apply(plugin = "org.jetbrains.kotlin.plugin.serialization")

            dependencies {
                implementation(project(":domain"))
                implementation(project(":data"))
                implementation(project(":core:designsystem"))
                implementation(libs.findLibrary("androidx.hilt.navigation.compose").get())
                implementation(libs.findLibrary("androidx.activity.compose").get())
                implementation(libs.findLibrary("androidx.navigation.compose").get())
                implementation(libs.findLibrary("androidx.lifecycle.runtime.compose").get())
                implementation(libs.findLibrary("androidx.lifecycle.viewModel.compose").get())
            }
        }
    }
}
