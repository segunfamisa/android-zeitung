plugins {
    id("java-library")
    id("kotlin")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}

dependencies {
    implementation(project(":shared:core"))
    implementation(project(":shared:utils"))

    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.arrow)
    implementation(libs.javax.inject)

    testImplementation(libs.test.junit)
    testImplementation(libs.test.mockito)
}
