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
    maven("https://jitpack.io")
}
dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
    compileOnly(files("libs/NusaItems-1.1.0.jar")) // optional integration
    compileOnly(files("libs/NusaMobs-1.0.0.jar"))
}
tasks.jar {
    archiveBaseName = "NusaSkills"
    archiveVersion = project.version.toString()
}
