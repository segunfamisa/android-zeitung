enableFeaturePreview("VERSION_CATALOGS")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "android-zeitung"

include(":android:app")
include(":android:bookmarks")
include(":android:common")
include(":android:headlines")
include(":android:news")
include(":android:onboarding")
include(":android:sources")

include(":shared:core")
include(":shared:data")
include(":shared:data-local")
include(":shared:data-remote")
include(":shared:domain")
include(":shared:utils")
