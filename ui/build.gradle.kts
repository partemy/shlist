plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("dev.partemy.gradle.common.library.android")
}

kotlin {
    jvmToolchain(11)
    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.ui)
                api(compose.foundation)
                api(compose.material3)
                api(compose.runtime)
                api(libs.androidx.lifecycle.viewmodel)
                api(libs.jetbrains.navigation.compose)
                api(libs.serialization)
                implementation(projects.common.resources)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(compose.preview)
            }
        }
    }
}

android {
    namespace = "dev.partemy.shlist.ui"
}