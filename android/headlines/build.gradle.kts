plugins {
    id("com.android.library")
    kotlin("android")
}
apply(from = "${rootProject.projectDir}/config/android-library-compose-config.gradle")

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.appcompat)

    // jetpack compose dependencies
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.animation)
    implementation(libs.compose.foundation.layout)
    implementation(libs.compose.material3)
    implementation(libs.compose.runtime.livedata)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling)

    testImplementation(libs.test.junit)
    androidTestImplementation(libs.androidx.testjunitext)
}