pluginManagement {
    repositories {
        mavenCentral()
        maven { url = uri("https://kotlin.bintray.com/kotlin-eap") }
        maven { url = uri("https://plugins.gradle.org/m2/") }
    }
}

rootProject.name = "ktor-hexagonal-multi-module-template"
include("domain", "ports", "adapters", "app")
