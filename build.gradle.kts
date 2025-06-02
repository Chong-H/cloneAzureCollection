plugins {
    java
    id("org.springframework.boot") version "3.4.0"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "edu.swjtu"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        JavaLanguageVersion.current().asInt() >= 21;
    }
}

repositories {
//    maven {
//        url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
//    }
//    maven {
//        url = uri("https://oss.sonatype.org/content/repositories/releases/")
//    }
    maven {
        url = uri("https://maven.aliyun.com/repository/public")// 阿里云公共仓库
    }
    maven {
        url = uri("https://maven.aliyun.com/repository/spring")// Spring 仓库（可选）
    }
    maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
    mavenCentral()
}
//repositories {
//    maven { url 'https://maven.aliyun.com/repository/public' }   // 阿里云公共仓库
//    maven { url 'https://maven.aliyun.com/repository/spring' }   // Spring 仓库（可选）
//    mavenCentral()
//}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web-services")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.mysql:mysql-connector-j")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
    implementation ("org.fisco-bcos.java-sdk:fisco-bcos-java-sdk:2.9.1")
    implementation ("jakarta.annotation:jakarta.annotation-api:2.1.1")

//    implementation("org.fisco-bcos:web3sdk:2.9.1")

}

tasks.withType<Test> {
    useJUnitPlatform()
}
