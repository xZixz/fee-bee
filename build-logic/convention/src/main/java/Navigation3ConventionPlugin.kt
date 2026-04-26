import com.cardes.buildconvention.implementation
import com.cardes.buildconvention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class Navigation3ConventionPlugin : Plugin<Project>{
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "org.jetbrains.kotlin.plugin.serialization")
            dependencies {
                implementation(libs.findLibrary("androidx.navigation3.ui").get())
                implementation(libs.findLibrary("androidx.navigation3.runtime").get())
                implementation(libs.findLibrary("androidx.lifecycle.viewmodel.navigation3").get())
                implementation(libs.findLibrary("androidx.material3.adaptive.navigation3").get())
                implementation(libs.findLibrary("kotlinx.serialization.core").get())
            }
        }
    }
}
