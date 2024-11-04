plugins {
    id("java")
}

group = "io.github.imecuadorian"
version = "0.0.1"

repositories {
    mavenCentral()
}

val lombokVersion = "1.18.34"

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")

    testCompileOnly("org.projectlombok:lombok:$lombokVersion")
    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    archiveBaseName.set("generic-library")
    archiveVersion.set("0.0.1")
    archiveClassifier.set("")
    destinationDirectory.set(file("D:\\libIntelliJ\\generic-library\\build\\libs"))
}