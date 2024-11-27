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
        val androidMain by getting {
            dependencies {
                api(libs.koin.android)
                api(libs.koin.androidx.compose)
            }
        }
        commonMain.dependencies {
            implementation(projects.common.data)
            implementation(projects.common.database)
            implementation(projects.common.domain)
            implementation(projects.common.preferences)
            implementation(projects.common.shlistApi)

            api(libs.koin.core)
            api(libs.koin.compose)
        }
        val iosMain by creating
    }
}

android {
    namespace = "dev.partemy.shlist.common.core"
}