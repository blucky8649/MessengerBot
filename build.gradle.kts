// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id("com.android.library") version "8.1.0" apply false
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
}

apply(from = "${rootProject.projectDir}/scripts/publish-root.gradle")