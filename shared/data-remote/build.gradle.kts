plugins {
    id("com.android.library")
    kotlin("kapt")
}
apply(from = "${rootProject.projectDir}/keys.gradle")
apply(from = "${rootProject.projectDir}/config/android-library-config.gradle")

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