plugins {
    id("com.android.library")
    kotlin("android")
}
apply(from = "${rootProject.projectDir}/config/android-library-compose-config.gradle")

dependencies {
    implementation(project(":android:common"))
    implementation(project(":shared:domain"))

    implementation(libs.kotlin.stdlib)

    implementation(platform(libs.compose.bom))
    implementation(libs.compose.foundation.layout)
    implementation(libs.compose.material)
    implementation(libs.compose.navigation)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling)
    implementation(libs.compose.runtime.livedata)

    implementation(libs.lifecycle.vm)
    implementation(libs.arrow)
    implementation(libs.dagger.core)

    testImplementation(libs.junit)
}