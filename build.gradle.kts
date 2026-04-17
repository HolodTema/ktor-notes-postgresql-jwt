val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "2.3.0"
    id("io.ktor.plugin") version "3.4.2"
    application
}

group = "com.terabyte"
version = "0.0.1"

application {
    mainClass = "com.terabyte.ktornotes.ApplicationKt"
}

kotlin {
    jvmToolchain(21)
}

ktor {
    fatJar {
        archiveFileName.set("ktor-notes-app.jar")
    }
}

dependencies {
    // to convert JSON from requests to data classes
    implementation("io.ktor:ktor-server-content-negotiation")
    implementation("io.ktor:ktor-serialization-kotlinx-json")

    // Authentication & JWT
    implementation("io.ktor:ktor-server-auth:2.3.0")
    implementation("io.ktor:ktor-server-auth-jwt:2.3.0")

    // to use application.yaml file
    implementation("io.ktor:ktor-server-config-yaml")

    // To hash passwords with BCrypt algorithm
    implementation("at.favre.lib:bcrypt:0.10.2")

    // Exposed ORM to work with PostgreSQL DB
    implementation("org.jetbrains.exposed:exposed-core:0.44.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.44.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.44.1")
    implementation("org.postgresql:postgresql:42.7.7")
    // to work with datetime fields of PostgreSQL and Exposed ORM
    implementation("org.jetbrains.exposed:exposed-java-time:0.44.1")

    // Ktor core
    implementation("io.ktor:ktor-server-core-jvm")
    // Netty is server which runs Ktor backend (like apache server or nginx etc)
    implementation("io.ktor:ktor-server-netty")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-core")
    implementation("io.ktor:ktor-serialization-gson:3.4.2")
    testImplementation("io.ktor:ktor-server-test-host")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}
