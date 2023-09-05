plugins {
    java
    id("org.springframework.boot") version "3.0.0"
    id("io.spring.dependency-management") version "1.1.0"

    // querydsl을 위한 추가 플러그인
    val kotlinVersion = "1.7.10"
    // KAPT(Kotlin Annotation Processing Tool)를 설치
    kotlin("kapt") version kotlinVersion
    // Intellij에서 사용할 파일을 생성하는 플러그인
    idea
}

group = "com.hou27"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation ("org.springframework.boot:spring-boot-starter-data-rest")
    implementation ("org.springframework.data:spring-data-rest-hal-explorer")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("com.mysql:mysql-connector-j")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // queryDSL 설정
    implementation("com.querydsl:querydsl-jpa")
    implementation("com.querydsl:querydsl-core")
    implementation("com.querydsl:querydsl-collections")
//    annotationProcessor("com.querydsl:querydsl-apt:${dependencyManagement.importedProperties["querydsl.version"]}:jpa") // querydsl JPAAnnotationProcessor 사용 지정
    kapt("com.querydsl:querydsl-apt:5.0.0:jpa") // querydsl JPAAnnotationProcessor 사용 지정
    annotationProcessor("jakarta.annotation:jakarta.annotation-api") // java.lang.NoClassDefFoundError (javax.annotation.Generated) 대응 코드
    annotationProcessor("jakarta.persistence:jakarta.persistence-api") // java.lang.NoClassDefFoundError (javax.annotation.Entity) 대응 코드
}

tasks.withType<Test> {
    useJUnitPlatform()
}


// Querydsl 설정부

/**
 * gradle kotlin version
 */

idea {
    module {
//        val generated = file("src/main/generated")
        val generated = file("build/generated/source/kapt/main")
        // java source set 에 querydsl QClass 위치 추가
        sourceDirs.add(generated)
        // querydsl QClass 파일 생성 위치를 지정
        generatedSourceDirs.add(generated)
    }
}