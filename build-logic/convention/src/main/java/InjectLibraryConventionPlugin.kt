import com.cardes.buildconvention.implementation
import com.cardes.buildconvention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class InjectLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                implementation(libs.findLibrary("javax.inject").get())
            }
        }
    }
}
