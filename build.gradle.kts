
buildscript {
    repositories {
        gradlePluginPortal()
    }

    dependencies {
        classpath("org.shipkit:shipkit-changelog:2.0.1")
        classpath("org.shipkit:shipkit-auto-version:2.0.4")
        classpath("io.github.gradle-nexus:publish-plugin:2.0.0-rc-2")
        classpath("com.gradleup.nmcp:nmcp:0.0.4")
    }
}

plugins {
    java
    `java-library`
    `maven-publish`
    id("com.gradleup.nmcp").version("0.0.4")
}

apply(from="gradle/java-publication.gradle.kts")
apply(from="gradle/shipkit.gradle")

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
