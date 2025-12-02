plugins {
    kotlin("jvm") version "2.2.21"
    kotlin("plugin.spring") version "2.2.21"
    kotlin("plugin.jpa") version "2.2.21"

    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.pasteleria"
version = "0.0.1-SNAPSHOT"
description = "Microservicio Pastelería Mil Sabores"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {

    // API REST (Web)
    implementation("org.springframework.boot:spring-boot-starter-web")

    // JPA / Hibernate
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Validaciones (@NotBlank, @NotNull, @Positive...)
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // JSON Kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // Reflexión Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // --- BASE DE DATOS: MySQL (CORRECTO) ---
    runtimeOnly("com.mysql:mysql-connector-j:8.0.33")

    // DevTools (solo en desarrollo)
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // Testing general
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll(
            "-Xjsr305=strict",
            "-Xannotation-default-target=param-property"
        )
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
