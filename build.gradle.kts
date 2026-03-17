// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.ksp) apply false
    id("org.jetbrains.kotlinx.kover") version "0.9.7"
    alias(libs.plugins.screenshot) apply false
    alias(libs.plugins.compose.compiler) apply false
}

dependencies {
    kover(project(":app"))
    kover(project(":core"))
    kover(project(":feature-expenses"))
}

kover {
    useJacoco("0.8.12")
    reports {
        filters {
            excludes {
                androidGeneratedClasses()
            }
        }
        total {
            xml {
                onCheck = true
            }
            html {
                onCheck = true
            }
        }
    }
}
