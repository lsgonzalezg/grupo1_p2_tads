plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.opencsv:opencsv:5.11")
    implementation(group = "org.projectlombok", name = "lombok", version = "1.18.38")
}

tasks.test {
    useJUnitPlatform()
}