plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.room)
    alias(libs.plugins.ksp)
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
                implementation(libs.room.runtime)
                implementation(libs.sqlite.bundled)
                implementation(libs.sqlite)
                implementation(libs.koin.core)
                implementation(projects.common.domain)
            }
        }
        val androidMain by getting
        val iosMain by creating
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

android {
    namespace = "dev.partemy.shlist.common.database"
}

dependencies {
    add("kspAndroid", libs.room.compiler)
    add("kspIosSimulatorArm64", libs.room.compiler)
    add("kspIosX64", libs.room.compiler)
    add("kspIosArm64", libs.room.compiler)
}