import org.gradle.api.tasks.testing.logging.TestLogEvent

val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val guiceVersion: String by project

plugins {
    base
    kotlin("jvm") version "1.3.0"
}

allprojects {
    group = "com.hyperanna.servicetemplate"
    version = "1.0"

    apply(plugin = "kotlin")

    repositories {
        mavenLocal()
        jcenter()
        maven { url = uri("https://kotlin.bintray.com/ktor") }
        maven { url = uri("https://plugins.gradle.org/m2/") }
    }

    tasks.withType<Test> {
        useJUnitPlatform {
            includeEngines("junit-jupiter")
        }

        failFast = true
        testLogging {
            events = setOf(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
        }
    }
}
