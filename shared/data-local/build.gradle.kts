plugins {
    id("com.android.library")
    kotlin("kapt")
}
apply(from = "${rootProject.projectDir}/config/android-library-config.gradle")

android {
    namespace = "com.segunfamisa.zeitung.data.local"
}

dependencies {

}
