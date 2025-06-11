plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.opencsv:opencsv:5.11")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(group = "org.projectlombok", name = "lombok", version = "1.18.38")
    //repositorio de Jackson para leer formato JSON
    implementation("com.fasterxml.jackson.core:jackson-databind:2.19.0")
}

tasks.test {
    useJUnitPlatform()
}