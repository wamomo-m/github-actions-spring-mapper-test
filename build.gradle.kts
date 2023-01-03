import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.0.1"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
    id("org.flywaydb.flyway") version "9.8.1"
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22"
}

java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.1")

    runtimeOnly("mysql:mysql-connector-java:8.0.30")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:5.5.4")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.2")
    testImplementation("org.mybatis.spring.boot:mybatis-spring-boot-starter-test:3.0.1")
}

buildscript {
    dependencies {
        classpath("org.flywaydb:flyway-mysql:9.4.0")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    dependsOn(tasks.flywayMigrate)
}

flyway {
    url = "jdbc:mysql://localhost:3306/mapper_test?useSSL=false"
    schemas = arrayOf("mapper_test")
    user = "root"
    password = "mysql"
    locations = arrayOf("filesystem:sql")
    baselineVersion = "1.0.0"
    baselineOnMigrate = true
}
