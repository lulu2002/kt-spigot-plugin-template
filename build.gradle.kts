import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import dev.s7a.gradle.minecraft.server.tasks.LaunchMinecraftServerTask
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    java
    `java-test-fixtures`
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("org.jetbrains.kotlin.jvm") version "1.9.23"
    id("dev.s7a.gradle.minecraft.server") version "3.1.0"
}

group = "me.lulu"
version = "1.0.0"

repositories {
    mavenCentral()
    maven {
        name = "spigotmc-repo"
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
    maven {
        name = "sonatype"
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }
    maven { url = uri("https://repo.imanity.dev/imanity-libraries/") }
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
    maven { url = uri("https://jitpack.io") }

    maven {
        name = "PlaceholderAPI"
        url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    }
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.20.4-R0.1-SNAPSHOT")
    compileOnly("org.mcwonderland:KotlinLib:1.9.23b1")
    compileOnly(kotlin("stdlib-jdk8"))

    implementation("com.google.inject:guice:7.0.0")

    implementation("com.github.shynixn.mccoroutine:mccoroutine-bukkit-api:2.15.0") {
        exclude(group = "org.jetbrains.kotlin")
    }
    implementation("com.github.shynixn.mccoroutine:mccoroutine-bukkit-core:2.15.0") {
        exclude(group = "org.jetbrains.kotlin")
    }


    implementation("com.github.Revxrsal.Lamp:common:3.2.1")
    implementation("com.github.Revxrsal.Lamp:bukkit:3.2.1")

    testImplementation("com.github.seeseemelk:MockBukkit-v1.20:3.80.0")
    testImplementation("io.mockk:mockk:1.13.10")
    testImplementation("org.mcwonderland:KotlinLib:1.9.23b1")

    testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    compileOnly("me.clip:placeholderapi:2.11.5")
    testImplementation("me.clip:placeholderapi:2.11.5")
}

tasks.withType(ShadowJar::class) {
    relocate("revxrsal.commands", "me.lulu.fruitteams.lib.commands")
    relocate("com.google", "me.lulu.fruitteams.lib.google")
    relocate("jakarta.inject", "me.lulu.fruitteams.lib.jakarta.inject")
    relocate("javax.annotation", "me.lulu.fruitteams.lib.javax.annotation")
    relocate("org.aopalliance", "me.lulu.fruitteams.lib.aopalliance")
    relocate("org.checkerframework", "me.lulu.fruitteams.lib.checkerframework")
    relocate("com.github.shynixn.mccoroutine", "me.lulu.fruitteams.lib.mccoroutine")
}

val targetJavaVersion = 17
java {
    val javaVersion = JavaVersion.toVersion(targetJavaVersion)
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
    if (JavaVersion.current() < javaVersion) {
        toolchain.languageVersion = JavaLanguageVersion.of(targetJavaVersion)
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"

    if (targetJavaVersion >= 10 || JavaVersion.current().isJava10Compatible()) {
        options.release.set(targetJavaVersion)
    }
}

tasks.named<ProcessResources>("processResources") {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

task<LaunchMinecraftServerTask>("testPlugin") {
    dependsOn("clean", "shadowJar")
    doFirst {
        copy {
            from(buildDir.resolve("libs/*.jar"))
            into(buildDir.resolve("MinecraftServer/plugins"))
        }
    }
    jarUrl.set(LaunchMinecraftServerTask.JarUrl.Paper("1.20.4"))
    agreeEula.set(true)
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-parameters")
}

tasks.withType<KotlinJvmCompile> {
    compilerOptions {
        javaParameters = true
    }
}