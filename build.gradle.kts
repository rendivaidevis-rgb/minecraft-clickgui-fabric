plugins {
    id("fabric-loom") version "1.4.11"
    id("maven-publish")
    kotlin("jvm") version "1.9.0"
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

version = "1.0.0"
group = "com.myclient"

repositories {
    mavenCentral()
    maven {
        name = "Fabric"
        url = uri("https://maven.fabricmc.net/")
    }
    maven {
        name = "Mojang"
        url = uri("https://libraries.minecraft.net/")
    }
}

dependencies {
    minecraft("com.mojang:minecraft:1.21.1")
    mappings(loom.officialMojangMappings())
    
    modImplementation("net.fabricmc:fabric-loader:0.16.5")
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.100.0+1.21.1")
    modImplementation("net.fabricmc:fabric-language-kotlin:1.12.0+kotlin.1.9.0")
}

loom {
    accessWidenerPath.set(file("src/main/resources/modclient.accesswidener"))
}

tasks {
    processResources {
        inputs.property("version", project.version)
        inputs.property("minecraft_version", "1.21.1")
        inputs.property("loader_version", "0.16.5")
        
        filesMatching("fabric.mod.json") {
            expand(mapOf(
                "version" to project.version,
                "minecraft_version" to "1.21.1"
            ))
        }
    }

    withType<JavaCompile> {
        options.release.set(21)
    }
}

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
    repositories {
        maven {
            url = uri(layout.buildDirectory.dir("libs"))
        }
    }
}
