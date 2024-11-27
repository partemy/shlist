plugins {
    `kotlin-dsl`
}

group = "dev.partemy.shlist.buildlogic"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("commonLibraryAndroid") {
            id = "dev.partemy.gradle.common.library.android"
            implementationClass =
                "dev.partemy.buildlogic.convention.CommonLibraryAndroidConventionPlugin"
        }
    }
}

