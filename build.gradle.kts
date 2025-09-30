
buildscript {
    repositories {
        gradlePluginPortal()
    }

    dependencies {
        classpath("org.shipkit:shipkit-changelog:2.0.1")
        classpath("org.shipkit:shipkit-auto-version:2.1.2")
        classpath("com.gradleup.nmcp:nmcp:1.2.0")
    }
}

plugins {
    java
    `java-library`
    `maven-publish`
    id("com.gradleup.nmcp").version("1.2.0")
}

apply(from="gradle/java-publication.gradle.kts")
apply(from="gradle/shipkit.gradle.kts")

group = "io.github.classbuddy4j"
description = "classbuddy4j"
version = "0.0.7"

repositories {
    mavenCentral()
}

val byteBuddyVersion = "1.17.7"

dependencies {
    implementation("net.bytebuddy:byte-buddy:$byteBuddyVersion")
    implementation("net.bytebuddy:byte-buddy-agent:$byteBuddyVersion")
    testImplementation(platform("org.junit:junit-bom:5.13.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.27.6")
}

tasks.test {
    useJUnitPlatform()
    failFast = true
    maxParallelForks = 1
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
    toolchain {
        languageVersion = JavaLanguageVersion.of(11)
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
