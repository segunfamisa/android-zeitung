plugins {
    id("java-library")
    id("kotlin")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}

dependencies {
    implementation(libs.arrow)
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.coroutines.core)

    implementation(libs.dagger.core)
}