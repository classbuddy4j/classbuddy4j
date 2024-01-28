plugins {
    id("java")
}

group = "io.github.classbuddy4j"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val byteBuddyVersion = "1.14.11"

dependencies {
    implementation("net.bytebuddy:byte-buddy:$byteBuddyVersion")
    implementation("net.bytebuddy:byte-buddy-agent:$byteBuddyVersion")
    testImplementation(platform("org.junit:junit-bom:5.10.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.25.2")
}

tasks.test {
    useJUnitPlatform()
}
