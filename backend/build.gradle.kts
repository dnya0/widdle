// ./gradlew clean build -Pspring.profiles.active=prod
// docker buildx build --platform=linux/amd64 -t widdle-app:prod -f ./Dockerfile --load .
// docker save widdle-app:prod > app.tar

plugins {
    kotlin("jvm") version "2.2.10"
    kotlin("plugin.spring") version "2.2.10"
    id("org.springframework.boot") version "3.5.4"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("plugin.jpa") version "2.2.10"
    kotlin("plugin.serialization") version "2.2.10"
}

group = "day.widdle"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    //spring
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    //webflux
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    val isAppleSilicon = System.getProperty("os.name") == "Mac OS X" && System.getProperty("os.arch") == "aarch64"
    if (isAppleSilicon) {
        runtimeOnly("io.netty:netty-resolver-dns-native-macos:4.1.72.Final:osx-aarch_64")
    }
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.10.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:1.10.2")

    //kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")

    //kotlin 직렬화/역직렬화
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    //aop
    implementation("org.springframework.boot:spring-boot-starter-aop")

    //database
    runtimeOnly("org.postgresql:postgresql")

    //test
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
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
