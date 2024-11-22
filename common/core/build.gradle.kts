plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    jvmToolchain(11)
    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        androidMain.dependencies {
            api(libs.koin.android)
            api(libs.koin.androidx.compose)
        }
        commonMain.dependencies {
            implementation(projects.common.data)
            implementation(projects.common.database)
            implementation(projects.common.domain)
            implementation(projects.common.preferences)

            api(libs.koin.core)
            api(libs.koin.compose)
        }
        iosMain.dependencies {

        }
    }
}

android {
    namespace = "dev.partemy.shlist.common.core"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}