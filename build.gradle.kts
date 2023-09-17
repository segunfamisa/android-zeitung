// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    extra.set("kotlin_version", "1.7.20")
    repositories {
        google()
        mavenCentral()
        maven(url = "https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath(libs.android.gradleplugin)
        classpath(libs.kotlin.gradleplugin)
        classpath(libs.protobuf.gradleplugin)
    }
}

plugins {
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(from = "${rootProject.projectDir}/ktlint.gradle")

    tasks {
        withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
            parallel = true
            buildUponDefaultConfig = true
            reports {
                xml.required.set(true)
                html {
                    required.set(true)
                    outputLocation.set(file("${buildDir.absolutePath}/reports/detekt.html"))
                }
            }
        }
    }
}
