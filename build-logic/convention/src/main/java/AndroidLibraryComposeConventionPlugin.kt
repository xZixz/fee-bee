import com.android.build.api.dsl.LibraryExtension
import com.cardes.buildconvention.configureCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.withPlugin("com.android.library") {
                apply(plugin = "org.jetbrains.kotlin.plugin.compose")
                val extension = extensions.getByType<LibraryExtension>()
                configureCompose(extension)
            }
        }
    }
}
