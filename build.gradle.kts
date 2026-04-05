plugins {
    java
    id("org.springframework.boot") version "3.5.4"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "org.trader"
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
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

    implementation("org.springframework.cloud:spring-cloud-starter-vault-config:3.1.3")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.vault:spring-vault-core:3.1.0")

    implementation("org.springframework.boot:spring-boot-loader")

    implementation("org.jasypt:jasypt:1.9.3")
    implementation("com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.4")
    implementation("org.apache.httpcomponents.core5:httpcore5:5.2.1")

    runtimeOnly("com.mysql:mysql-connector-j:8.3.0")

    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.projectlombok:lombok")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")

    // MapStruct
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")

    // JUnit (уже есть в spring-boot-starter-test) Базовый набор
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // RestAssured. библиотека специально для тестирования REST API
    testImplementation("io.rest-assured:rest-assured:5.4.0")

    // JSON support (если понадобится проверять JSONPath)
    testImplementation("io.rest-assured:json-path:5.4.0")
    testImplementation("io.rest-assured:json-schema-validator:5.4.0")

    // Для удобных assert'ов. Улучшенный инструмент
    testImplementation("org.assertj:assertj-core:3.25.3")
}

tasks.withType<Test> {
    useJUnitPlatform()
    jvmArgs = listOf("-Xshare:off")
}

// Отключаем тонкий jar
tasks.jar {
    enabled = false
}

tasks.bootJar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes["Main-Class"] = "org.springframework.boot.loader.launch.JarLauncher"
        attributes["Start-Class"] = "org.trader.TraderApplication"
    }
    requiresUnpack("org.springframework.boot:spring-boot-loader")
}

/*tasks.bootJar {
    // Дубликаты исключаем
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    // Эту часть удаляем, чтобы плагин Spring Boot сам сгенерировал корректные атрибуты Manifest
    manifest {
        attributes["Main-Class"] = "org.springframework.boot.loader.launch.JarLauncher" // Плагин сделает это сам
        attributes["Start-Class"] = "org.trader.TraderApplication" // Плагин найдет это сам
    }
    requiresUnpack("org.springframework.boot:spring-boot-loader")

    // Если у вас не сработает автоматическое определение, можете явно указать Start-Class:
    // mainClass.set("org.trader.TraderApplication")
}*/

