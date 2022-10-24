plugins {
    id("com.android.library")
    kotlin("android")
}
apply(from = "${rootProject.projectDir}/config/android-library-compose-config.gradle")

dependencies {
    implementation(libs.kotlin.stdlib)

    // compose dependencies
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.foundation.layout)
    implementation(libs.compose.material)
    implementation(libs.compose.constraintlayout)

    implementation(libs.coil)
}