plugins {
    kotlin("jvm")
    kotlin("kapt")
    id("idea")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(project(":shared:core"))
    implementation(project(":shared:domain"))

    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.coroutines.core)

    implementation(libs.arrow)

    implementation(libs.dagger.core)
    kapt(libs.dagger.compiler)
    implementation(libs.pagingmultiplatform.common)
    implementation(libs.timber)

    testImplementation(libs.test.junit)
    testImplementation(libs.test.mockito)
}
