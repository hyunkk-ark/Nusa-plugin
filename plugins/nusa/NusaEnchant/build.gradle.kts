plugins {
    java
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.github.cahyunkk"
version = "1.0.0"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.11.6")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7.1")
}

tasks.shadowJar {
    archiveBaseName.set("NusaEnchant")
    archiveClassifier.set("")
    archiveVersion.set(project.version.toString())
}