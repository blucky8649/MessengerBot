import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
    id("signing")
}

android {
    namespace = "com.example.messengerbot"
    compileSdk = 33

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
afterEvaluate {
    val GROUP_ID = "io.github.blucky8649"
    val ARTIFACT_ID = "MessengerBot"
    val VERSION = "0.0.2"

    publishing {

        publications {
            create<MavenPublication>("release") {
                groupId = GROUP_ID
                artifactId = ARTIFACT_ID
                version = VERSION

                artifact("$buildDir/outputs/aar/${artifactId}-release.aar")

                pom {
                    name.set("MessengerBot")
                    groupId = GROUP_ID
                    description.set("you can easily create your own messenger bots for any messenger apps")
                    url.set("https://github.com/blucky8649/MessengerBot")
                    licenses {
                        license {
                            name.set("MIT license")
                            url.set("https://opensource.org/licenses/MIT")
                        }
                    }
                    developers {
                        developer {
                            id.set("blucky8649")
                            name.set("DongYeon-Lee")
                            email.set("blucky8649@gmail.com")
                        }
                    }
                    scm {
                        url.set("https://github.com/blucky8649/MessengerBot.git")
                    }
                }
            }
        }
    }
}


tasks {
    val sourcesJar by creating(Jar::class) {
        archiveClassifier.set("sources")
        from(android.sourceSets.getByName("main").java.srcDirs)
    }

    val javadocJar by creating(Jar::class) {
        val javadoc by tasks
        from(javadoc)
        archiveClassifier.set("javadoc")
    }
    artifacts {
        archives(sourcesJar)
        archives(javadocJar)
    }
}

signing {
    useInMemoryPgpKeys(
        rootProject.ext["signing.keyId"] as String,
        rootProject.ext["signing.key"] as String,
        rootProject.ext["signing.password"] as String,
    )

    sign(publishing.publications)

    sign(configurations.archives.name)
}


dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}