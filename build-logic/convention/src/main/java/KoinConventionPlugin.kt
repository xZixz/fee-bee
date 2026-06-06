import com.cardes.buildconvention.implementation
import com.cardes.buildconvention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KoinConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                implementation(platform(libs.findLibrary("koin.bom").get()))
                implementation(libs.findLibrary("koin.core").get())
                implementation(libs.findLibrary("koin.android").get())
                implementation(libs.findLibrary("koin.compose.viewmodel").get())
                implementation(libs.findLibrary("koin.annotations").get())
            }
        }
    }
}
