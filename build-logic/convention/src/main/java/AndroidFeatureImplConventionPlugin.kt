import com.cardes.buildconvention.implementation
import com.cardes.buildconvention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureImplConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "feebee.android.library")
            apply(plugin = "feebee.koin")
            apply(plugin = "feebee.compose.library")
            apply(plugin = "feebee.nav3")
            apply(plugin = "org.jetbrains.kotlin.plugin.serialization")

            dependencies {
                implementation(project(":core:domain"))
                implementation(project(":core:data"))
                implementation(project(":core:designsystem"))
                implementation(project(":core:ui"))
                implementation(project(":core:common"))
                implementation(project(":core:navigation"))
                implementation(libs.findLibrary("koin.androidx.compose").get())
                implementation(libs.findLibrary("androidx.activity.compose").get())
                implementation(libs.findLibrary("androidx.lifecycle.runtime.compose").get())
                implementation(libs.findLibrary("androidx.lifecycle.viewModel.compose").get())
            }
        }
    }
}
