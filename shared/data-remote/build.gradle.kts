plugins {
    kotlin("jvm")
    kotlin("kapt")
    id("de.fuerstenau.buildconfig").version("1.1.8")
    id("idea")
}
apply(from = "${rootProject.projectDir}/keys.gradle")

val compile by configurations.creating

buildscript {
    dependencies {
        classpath("gradle.plugin.de.fuerstenau:BuildConfigPlugin:1.1.8")
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

val apiKey: groovy.lang.Closure<Any?> by extra
buildConfig {
    clsName = "BuildConfig"
    packageName = "com.segunfamisa.zeitung.data"

    println("Extras: ${rootProject.extra("apiKey")}")
    buildConfigField("String", "BaseUrl", "https://newsapi.org/v2/")
    buildConfigField("String", "ApiKey", "${apiKey()}")
}

dependencies {
    implementation(project(":shared:core"))
    implementation(project(":shared:domain"))
    implementation(project(":shared:data"))

    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.coroutines.core)

    implementation(libs.dagger.core)

    val kapt by configurations
    kapt(libs.dagger.compiler)

    implementation(libs.arrow)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.moshi)

    testImplementation(libs.test.junit)
    testImplementation(libs.test.mockwebserver) {
        because("version 3.12.0 uses okhttp version 3.12.0 which is incompatible with retrofit 2.4.0")
    }
    testImplementation(libs.test.mockito)
}