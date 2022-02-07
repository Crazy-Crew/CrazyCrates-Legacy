plugins {
    id("com.github.johnrengelman.shadow") version "7.1.1"
}

repositories {

    maven("https://repo.triumphteam.dev/snapshots")

    maven {
        url = uri("https://repo.codemc.org/repository/maven-public/")
        content {
            includeGroup("de.tr7zw")
            includeGroup("com.gmail.filoghost.holographicdisplays")
        }
    }

    maven {
        url = uri("https://jitpack.io")
        content {
            includeGroup("com.github.decentsoftware-eu")
        }
    }

    maven {
        url = uri("https://papermc.io/repo/repository/maven-public/")
        content {
            includeGroup("io.papermc")
        }
    }

    maven {
        url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/")
        content {
            includeGroup("me.clip")
        }
    }

    maven {
        url = uri("https://repo.mvdw-software.com/content/groups/public/")
        content {
            includeGroup("be.maximvdw")
        }
    }
}

dependencies {
    compileOnly("me.clip:placeholderapi:2.11.1") {
        exclude(group = "org.spigotmc", module = "spigot")
    }

    compileOnly("com.gmail.filoghost.holographicdisplays:holographicdisplays-api:2.4.9")

    compileOnly("be.maximvdw:MVdWPlaceholderAPI:3.1.1-SNAPSHOT") {
        exclude(group = "org.spigotmc", module = "spigot")
        exclude(group = "be.maximvdw", module = "MVdWUpdater")
    }

    compileOnly("com.github.decentsoftware-eu:decentholograms:2.2.5")

    implementation("de.tr7zw:item-nbt-api:2.9.1")
    implementation("io.papermc:paperlib:1.0.7")

    implementation("org.bstats:bstats-bukkit:3.0.0")

    //implementation("dev.triumphteam:triumph-cmd-bukkit:2.0.0-SNAPSHOT")

    // Api Module
    implementation(project(":api"))
}

tasks {
    shadowJar {
        archiveFileName.set("Crazy-Crates-[v${rootProject.version}].jar")

        relocate("de.tr7zw", "com.badbones69.crazycrates.libs")
        relocate("io.papermc.lib", "com.badbones69.crazycrates.libs")
        relocate("org.bstats", "com.badbones69.crazycrates.libs")
        //relocate("dev.triumphteam", "com.badbones69.crazycrates.libs")
    }
}