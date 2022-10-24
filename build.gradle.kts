// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    extra.set("kotlin_version", "1.7.20")
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.3.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${project.extra["kotlin_version"]}")
    }
}

plugins {
    id("io.gitlab.arturbosch.detekt").version("1.10.0")
    id("org.jlleitschuh.gradle.ktlint").version("9.2.1")
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

apply(from = "${rootProject.projectDir}/dependencies.gradle")

subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(from = "${rootProject.projectDir}/ktlint.gradle")

    tasks {
        withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
            parallel = true
            buildUponDefaultConfig = true
            reports {
                xml.enabled = true
                html {
                    enabled = true
                    destination = file("${buildDir.absolutePath}/reports/detekt.html")
                }
            }
        }
    }
}
