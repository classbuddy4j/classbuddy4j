
import com.vanniktech.maven.publish.SonatypeHost

plugins {
    id("java-library")
    id("com.vanniktech.maven.publish") version "0.27.0"
}

group = "io.github.classbuddy4j"
description = "classbuddy4j"
version = "0.0.1-SNAPSHOT"

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
    failFast = true
    maxParallelForks = 1
}

mavenPublishing {
  publishToMavenCentral(SonatypeHost.DEFAULT)

  signAllPublications()
}
