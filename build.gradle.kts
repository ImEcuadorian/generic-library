plugins {
    application
    id("java")
    id("io.freefair.lombok") version "8.13.1"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "io.github.imecuadorian.library"
version = "0.0.1"

repositories {
    mavenCentral()
}

val jetbrainsAnnotationsVersion = "26.0.2"

dependencies {
    implementation("org.jetbrains:annotations:$jetbrainsAnnotationsVersion")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    archiveBaseName.set("generic-library")
    archiveVersion.set("0.0.1")
    archiveClassifier.set("")
}