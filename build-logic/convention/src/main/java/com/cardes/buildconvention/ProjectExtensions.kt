package com.cardes.buildconvention

import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.getByType

fun DependencyHandler.implementation(any: Any): Dependency? = add("implementation", any)

val Project.libs
    get(): VersionCatalog = extensions
        .getByType<VersionCatalogsExtension>()
        .named("libs")
