plugins {
    id("com.android.library")
    kotlin("android")
}
apply(from = "${rootProject.projectDir}/config/android-library-compose-config.gradle")

android {
    namespace = "com.segunfamisa.zeitung.news.ui"
}

dependencies {
    implementation(project(":android:common"))
    implementation(project(":shared:domain"))
    implementation(project(":shared:core"))
    implementation(project(":shared:utils"))

    implementation(libs.kotlin.stdlib)

    implementation(platform(libs.compose.bom))
    implementation(libs.compose.animation)
    implementation(libs.compose.constraintlayout)
    implementation(libs.compose.foundation.layout)
    implementation(libs.compose.foundation.android)
    implementation(libs.compose.material3)
    implementation(libs.compose.runtime.livedata)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling)
    implementation(libs.compose.navigation)
    implementation(libs.compose.windowsizeclass)

    implementation(libs.arrow)
    implementation(libs.lifecycle.vm)
    implementation(libs.coil)

    implementation(libs.dagger.core)

    implementation(libs.bundles.kotlin.coroutines)

    testImplementation(libs.test.compose)
    debugImplementation(libs.test.compose.manifests)
    testImplementation(libs.test.junit)
    testImplementation(libs.test.robolectric)
    androidTestImplementation(libs.androidx.testjunitext)
}