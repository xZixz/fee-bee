@file:Suppress("UnstableApiUsage")

include(":core:ui")

include(":feature:categories")

include(":feature:analytics")

include(":core:designsystem")

include(":feature:spendings")

include(":feature:editcategory")

include(":feature:spendingdetail")

include(":feature:editspending")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
rootProject.name = "FeeBee"
include(":app")
include(":core:domain")
include(":core:data")
include(":core:common")
