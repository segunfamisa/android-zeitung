plugins {
    id("com.android.library")
    kotlin("android")
}
apply(from = "${rootProject.projectDir}/config/android-library-compose-config.gradle")

android {
    namespace = "com.segunfamisa.zeitung.bookmarks"
}

dependencies {
    implementation(project(":android:common"))
    implementation(project(":android:news-ui"))
    implementation(project(":shared:core"))
    implementation(project(":shared:domain"))
    implementation(project(":shared:utils"))

    implementation(libs.kotlin.stdlib)
    implementation(libs.bundles.kotlin.coroutines)

    implementation(libs.arrow)

    implementation(platform(libs.compose.bom))
    implementation(libs.compose.foundation.layout)
    implementation(libs.compose.material3)
    implementation(libs.compose.navigation)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling)

    implementation(libs.coil)
    implementation(libs.dagger.core)
    implementation(libs.lifecycle.vm)

    testImplementation(libs.test.junit)
}
