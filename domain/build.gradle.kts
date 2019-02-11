plugins {
    kotlin("jvm")
}

dependencies {
    compile(project(":ports"))
    testCompile("io.kotlintest:kotlintest-assertions:3.1.10")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.1.0")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.0.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.1.0")
}
