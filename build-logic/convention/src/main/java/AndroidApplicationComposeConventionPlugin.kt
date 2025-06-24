import com.android.build.api.dsl.ApplicationExtension
import com.cardes.buildconvention.configureCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.withPlugin("com.android.application") {
                apply(plugin = "org.jetbrains.kotlin.plugin.compose")
                val extension = extensions.getByType<ApplicationExtension>()
                configureCompose(extension)
            }
        }
    }
}
