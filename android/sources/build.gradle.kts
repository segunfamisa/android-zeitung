plugins {
    id("com.android.library")
    kotlin("android")
}
apply(from = "${rootProject.projectDir}/config/android-library-compose-config.gradle")

android {
    namespace = "com.segunfamisa.zeitung.sources"
}

dependencies {
    implementation(project(":android:common"))
    implementation(project(":shared:core"))
    implementation(project(":shared:domain"))
    implementation(project(":shared:utils"))

    implementation(libs.kotlin.stdlib)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.foundation.layout)
    implementation(libs.compose.material3)
    implementation(libs.compose.navigation)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling)

    implementation(libs.lifecycle.vm)
    implementation(libs.arrow)
    implementation(libs.dagger.core)

    testImplementation(libs.test.junit)
}
