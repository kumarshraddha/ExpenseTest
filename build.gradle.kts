// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.screenshot) apply false
    alias(libs.plugins.compose.compiler) apply false
}

val jacocoVersion = libs.versions.jacoco.get()

allprojects {
    apply(plugin = "jacoco")

    configure<JacocoPluginExtension> {
        toolVersion = jacocoVersion
    }
}

val subProjects = listOf("app", "core", "feature-expenses")

tasks.register<JacocoReport>("jacocoFullReport") {
    group = "Reporting"
    description = "Generate Jacoco coverage reports for all modules"

    dependsOn(subProjects.map { ":$it:testDebugUnitTest" })

    val classDirectories = files()
    val sourceDirectories = files()
    val executionData = files()

    subProjects.forEach { projectName ->
        val project = project(":$projectName")
        val buildDir = project.layout.buildDirectory.get().asFile
        
        classDirectories.from(
            fileTree("$buildDir/tmp/kotlin-classes/debug") {
                exclude(
                    "**/R.class",
                    "**/R$*.class",
                    "**/BuildConfig.*",
                    "**/Manifest*.*",
                    "**/*Test*.*",
                    "android/**/*.*",
                    "**/di/**",
                    "**/*_Factory*.*",
                    "**/*_MembersInjector*.*",
                    "**/*_Provides*.*",
                    "**/ui/theme/**"
                )
            }
        )
        sourceDirectories.from(files("${project.projectDir}/src/main/java"))
        executionData.from(fileTree(buildDir) {
            include("outputs/unit_test_code_coverage/debugUnitTest/testDebugUnitTest.exec")
        })
    }

    reports {
        xml.required.set(true)
        html.required.set(true)
    }

    this.classDirectories.setFrom(classDirectories)
    this.sourceDirectories.setFrom(sourceDirectories)
    this.executionData.setFrom(executionData)
}

