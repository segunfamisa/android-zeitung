plugins {
    id("com.android.library")
    kotlin("kapt")
}
apply(from = "${rootProject.projectDir}/config/android-library-config.gradle")

android {
    namespace = "com.segunfamisa.zeitung.data.local"
}

dependencies {
    implementation(project(":shared:core"))
    implementation(project(":shared:domain"))
    implementation(project(":shared:data"))

    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.coroutines.core)

    implementation(libs.dagger.core)
    implementation(libs.arrow)

    testImplementation(libs.test.junit)
    testImplementation(libs.test.mockito)
}
