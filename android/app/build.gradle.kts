@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.application")
    kotlin("kapt")
}
apply(from = "${rootProject.projectDir}/config/android-compose-config.gradle")
apply(from = "${rootProject.projectDir}/keys.gradle")

val apiKey: String by rootProject.extra


android {
    defaultConfig {
        applicationId = "com.segunfamisa.zeitung"
        versionCode = 1
        versionName = "1.0"


        buildConfigField("String", "BaseUrl", "\"https://newsapi.org/v2/\"")
        buildConfigField("String", "ApiKey", "\"$apiKey\"")
    }
}

dependencies {
    val kapt by configurations

    implementation(project(":android:common"))
    implementation(project(":android:news"))
    implementation(project(":android:onboarding"))
    implementation(project(":android:sources"))
    implementation(project(":shared:core"))
    implementation(project(":shared:data"))
    implementation(project(":shared:data-local"))
    implementation(project(":shared:data-remote"))
    implementation(project(":shared:domain"))
    implementation(project(":shared:utils"))

    implementation(libs.kotlin.stdlib)
    implementation(libs.bundles.kotlin.coroutines)

    implementation(libs.arrow)

    implementation(platform(libs.compose.bom))
    implementation(libs.compose.animation)
    implementation(libs.compose.constraintlayout)
    implementation(libs.compose.foundation.layout)
    implementation(libs.compose.foundation.android)
    implementation(libs.compose.material3)
    implementation(libs.compose.navigation)
    implementation(libs.compose.runtime.livedata)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling)
    implementation(libs.compose.windowsizeclass)

    implementation(libs.coil)
    implementation(libs.datastore)

    implementation(libs.appcompat)
    implementation(libs.lifecycle.vm)
    implementation(libs.material)

    implementation(libs.moshi.converter)

    implementation(libs.glide.lib)
    kapt(libs.glide.kapt)

    implementation(libs.dagger.core)
    implementation(libs.dagger.android)
    implementation(libs.dagger.android.support)
    kapt(libs.dagger.compiler)
    kapt(libs.dagger.android.processor)

    testImplementation(libs.test.junit)
    testImplementation(libs.test.robolectric)
    testImplementation(libs.test.compose)
    debugImplementation(libs.test.compose.manifests)
    androidTestImplementation(libs.androidx.testrunner)
    androidTestImplementation(libs.androidx.espresso)
    androidTestImplementation(libs.test.compose)
}
