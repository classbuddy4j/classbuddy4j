
buildscript {
    repositories {
        gradlePluginPortal()
    }

    dependencies {
        classpath("org.shipkit:shipkit-changelog:2.0.1")
        classpath("org.shipkit:shipkit-auto-version:2.1.2")
        classpath("com.gradleup.nmcp:nmcp:0.0.9")
    }
}

plugins {
    java
    `java-library`
    `maven-publish`
    id("com.gradleup.nmcp").version("0.0.9")
}

apply(from="gradle/java-publication.gradle.kts")
apply(from="gradle/shipkit.gradle.kts")

group = "io.github.classbuddy4j"
description = "classbuddy4j"
version = "0.0.7"

repositories {
    mavenCentral()
}

val byteBuddyVersion = "1.18.2"

dependencies {
    implementation("net.bytebuddy:byte-buddy:$byteBuddyVersion")
    implementation("net.bytebuddy:byte-buddy-agent:$byteBuddyVersion")
    testImplementation(platform("org.junit:junit-bom:6.0.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.27.6")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testRuntimeOnly("org.junit.platform:junit-platform-engine")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
    failFast = true
    maxParallelForks = 1
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

nmcp {
    if (System.getenv("NEXUS_TOKEN_PWD") != null) {
        publish("mavenJava") {
            username = System.getenv("NEXUS_TOKEN_USER")
            password = System.getenv("NEXUS_TOKEN_PWD")
            publicationType = "AUTOMATIC"
        }
    }
}
