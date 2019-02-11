val kotlinVersion: String by rootProject
val guiceVersion: String by rootProject

plugins {
    kotlin("jvm")
}

dependencies {
    compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    compile("com.google.inject:guice:$guiceVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.1.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.1.0")
}
