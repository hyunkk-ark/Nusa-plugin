plugins {
    java
    id("com.github.johnrengelman.shadow") version "8.1.1"
}
group = "com.github.cahyunkk"
version = "1.0.0"
java { toolchain.languageVersion = JavaLanguageVersion.of(21) }
tasks.compileJava { options.encoding = "UTF-8"; options.release = 21 }
repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}
dependencies { compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT") }
tasks.jar { archiveBaseName = "NusaRPG"; archiveVersion = project.version.toString() }
