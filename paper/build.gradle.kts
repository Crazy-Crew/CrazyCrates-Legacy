plugins {
    id("com.github.johnrengelman.shadow")

    id("com.modrinth.minotaur")

    id("xyz.jpenilla.run-paper")

    id("crazycrates.paper-plugin")

    `maven-publish`
}

releaseBuild {
    tasks {
        shadowJar {
            archiveFileName.set("${getProjectName()}+${getProjectVersion()}.jar")

            listOf(
                "de.tr7zw",
                "org.bstats",
                "dev.triumphteam.cmd"
            ).forEach { value ->
                relocate(value, "${getProjectGroup()}.plugin.library.$value")
            }
        }

        runServer {
            minecraftVersion("1.19.3")
        }

        modrinth {
            token.set(System.getenv("MODRINTH_TOKEN"))
            projectId.set(getProjectName().toLowerCase())

            versionName.set("${getProjectName()} ${getProjectVersion()}")
            versionNumber.set(getProjectVersion())

            versionType.set(getProjectType())

            uploadFile.set(shadowJar.get())

            autoAddDependsOn.set(true)

            gameVersions.addAll(listOf("1.18", "1.18.1", "1.18.2", "1.19", "1.19.1", "1.19.2", "1.19.3"))
            loaders.addAll(listOf("paper", "purpur"))

            //<h3>The first release for CrazyCrates on Modrinth! 🎉🎉🎉🎉🎉<h3><br> If we want a header.
            changelog.set("""
                <h4>Changes:</h4>
                 <p>Added the ability to send a different message if they got a virtual key due to their inventory being full.</p>
                 <p>Give-Virtual-Keys-When-Inventory-Full-Message: true : A new option in the config.yml</p>
                 <p>Cannot-Give-Player-Keys : A new message in messages.yml</p>
                <h4>Bug Fixes:</h4>
                 <p>N/A</p>
            """.trimIndent())
        }

        processResources {
            filesMatching("plugin.yml") {
                expand(
                    "name" to getProjectName(),
                    "group" to getProjectGroup(),
                    "version" to getProjectVersion(),
                    "description" to getProjectDescription(),
                    "website" to "https://modrinth.com/${getExtension()}/${getProjectName().toLowerCase()}"
                )
            }
        }
    }

    publishing {
        repositories {
            val urlExt = if (isBeta()) "beta" else "releases"
            maven("https://repo.crazycrew.us/$urlExt") {
                name = "crazycrew"
                // Used for locally publishing.
                // credentials(PasswordCredentials::class)

                credentials {
                    username = System.getenv("REPOSITORY_USERNAME")
                    password = System.getenv("REPOSITORY_PASSWORD")
                }
            }
        }

        publications {
            create<MavenPublication>("maven") {
                groupId = getProjectGroup()
                artifactId = "${getProjectName().toLowerCase()}-paper"
                version = getProjectVersion()
                from(components["java"])
            }
        }
    }
}