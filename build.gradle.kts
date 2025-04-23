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
val junitVersion = "5.12.1"
val jetbrainsAnnotationsVersion = "26.0.2"

dependencies {
    implementation("org.jetbrains:annotations:$jetbrainsAnnotationsVersion")
    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter:$junitVersion")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    archiveBaseName.set("generic-library")
    archiveVersion.set("0.0.1")
    archiveClassifier.set("")
}

application {
    mainClass.set("io.github.imecuadorian.library.MainKt")
}

tasks.shadowJar {
    archiveBaseName.set("generic-library")
    archiveVersion.set("0.0.1")
    archiveClassifier.set("")
    mergeServiceFiles()
    minimize()
}