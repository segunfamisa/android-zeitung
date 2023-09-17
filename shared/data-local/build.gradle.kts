plugins {
    id("com.android.library")
    kotlin("kapt")
}
apply(from = "${rootProject.projectDir}/config/android-library-config.gradle")
apply(from = "${rootProject.projectDir}/config/data-store-config.gradle")

android {
    namespace = "com.segunfamisa.zeitung.data.local"
}

dependencies {
    implementation(project(":shared:core"))
    implementation(project(":shared:domain"))
    implementation(project(":shared:data"))

    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.coroutines.core)

    implementation(libs.arrow)
    implementation(libs.dagger.core)
    implementation(libs.datastore)

    val kapt by configurations
    kapt(libs.dagger.compiler)

    testImplementation(libs.test.junit)
    testImplementation(libs.test.mockito)
}
