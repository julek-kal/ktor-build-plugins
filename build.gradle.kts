import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    @Suppress("DSL_SCOPE_VIOLATION") // "libs" produces a false-positive warning, see https://youtrack.jetbrains.com/issue/KTIJ-19369
    alias(libs.plugins.kotlin.jvm)
    `maven-publish`
    java
}

repositories.mavenCentral()

subprojects {
    apply(plugin =  "maven-publish")
    apply(plugin ="java")
    repositories.mavenCentral()
    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging.events(*TestLogEvent.values())
    }
    apply(plugin = rootProject.libs.plugins.kotlin.jvm.get().pluginId)
    dependencies {
        implementation(rootProject.libs.ktor.server.core)
        implementation(rootProject.libs.ktor.server.cio)
        implementation(rootProject.libs.logback)
    }
    publishing {
        publications{
            create<MavenPublication>("maven") {
                groupId = project.group.toString()
                artifactId = project.name.toString()
                version = project.version.toString()
                from(components["java"])
            }
        }
    }
}



