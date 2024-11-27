plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    id("dev.partemy.gradle.common.library.android")
}

kotlin {
    jvmToolchain(11)
    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        androidMain.dependencies {

        }
        commonMain.dependencies {
            implementation(projects.common.domain)
            implementation(projects.common.database)
            implementation(projects.common.preferences)
            implementation(projects.common.shlistApi)
        }
        iosMain.dependencies {

        }
    }
}

android {
    namespace = "dev.partemy.shlist.common.data"
}