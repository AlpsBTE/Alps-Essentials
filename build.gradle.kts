plugins {
    java
    `maven-publish`
    alias(libs.plugins.shadow)
}

repositories {
    // mavenLocal() // NEVER use in Production/Commits!
    maven {
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }

    maven {
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }

    maven {
        url = uri("https://mvn.alps-bte.com/repository/alps-bte/")
    }

    maven {
        url = uri("https://maven.enginehub.org/repo/")
    }

    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    implementation(libs.com.alpsbte.alpslib.alpslib.utils)
    implementation(libs.com.alpsbte.alpslib.alpslib.io)
    implementation(libs.org.spongepowered.configurate.yaml)
    compileOnly(libs.io.papermc.paper.paper.api)
    compileOnly(libs.li.cinnazeyy.langlibs.api)
    compileOnly(libs.com.sk89q.worldguard.worldguard.bukkit)
}

group = "com.alpsbte"
version = "1.5.2"
description = "Alps-Essentials"
java.sourceCompatibility = JavaVersion.VERSION_21
java.targetCompatibility = JavaVersion.VERSION_21

tasks.shadowJar {
    archiveClassifier = ""

    relocationPrefix = "com.alpsbte.essentials.shaded"
    enableAutoRelocation = true
}

tasks.assemble {
    dependsOn(tasks.shadowJar) // Ensure that the shadowJar task runs before the build task
}

tasks.jar {
    archiveClassifier = "UNSHADED"
    enabled = false // Disable the default jar task since we are using shadowJar
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<GenerateModuleMetadata> {
    enabled = false
}


tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
}

java {
    withSourcesJar()
    withJavadocJar()
}

tasks.processResources {
    // work around IDEA-296490
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    with(copySpec {
        from("src/main/resources/plugin.yml") {
            expand(
                mapOf(
                    "version" to project.version,
                    "description" to project.description
                )
            )
        }
    })
}
