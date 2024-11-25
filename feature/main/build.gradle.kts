plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    jvmToolchain(11)
    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
        }
        commonMain.dependencies {
            implementation(projects.ui)
            implementation(projects.common.resources)
            implementation(projects.common.domain)
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
        }
        iosMain.dependencies {

        }
    }
}

android {
    namespace = "dev.partemy.shlist.feature.main"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}