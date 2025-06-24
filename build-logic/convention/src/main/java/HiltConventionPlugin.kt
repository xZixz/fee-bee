import com.cardes.buildconvention.implementation
import com.cardes.buildconvention.libs
import dagger.hilt.android.plugin.HiltExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class HiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.google.devtools.ksp")
            dependencies {
                "ksp"(libs.findLibrary("hilt.compiler").get())
            }
            pluginManager.withPlugin("com.android.base") {
                apply(plugin = "com.google.dagger.hilt.android")

                dependencies {
                    implementation(libs.findLibrary("hilt.android").get())
                }

                extensions.configure<HiltExtension> {
                    enableAggregatingTask = true
                }
            }
        }
    }
}
