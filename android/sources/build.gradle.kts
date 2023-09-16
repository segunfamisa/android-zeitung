plugins {
    id("com.android.library")
    kotlin("android")
}
apply(from = "${rootProject.projectDir}/config/android-library-compose-config.gradle")

android {
    namespace = "com.segunfamisa.zeitung.sources"
}

dependencies {

}
