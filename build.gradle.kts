buildscript {
    repositories {
        gradlePluginPortal()
    }
}

plugins {
    checkstyle
}

project.extra["GithubUrl"] = "https://github.com/Bloodspawns/oprs-plugins"
val releaseDir = "${rootProject.projectDir.parent}/Bloodspawns-oprs-release"

apply<BootstrapPlugin>()

subprojects {
    group = "com.example"

    project.extra["PluginProvider"] = "Bloodspawns"
    project.extra["ProjectSupportUrl"] = "https://github.com/Bloodspawns/oprs-plugins"
    project.extra["PluginLicense"] = "3-Clause BSD License"

    repositories {
        jcenter {
            content {
                excludeGroupByRegex("com\\.openosrs.*")
            }
        }

//        exclusiveContent {
//            forRepository {
//                mavenLocal()
//            }
//            filter {
//                includeGroupByRegex("com\\.openosrs.*")
//            }
//        }
        mavenCentral()
        mavenLocal()
    }

    apply<JavaPlugin>()

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    val externalManagerDirectory: String = project.findProperty("externalManagerDirectory")?.toString()
        ?: (System.getProperty("user.home") + "/.openosrs/plugins")

    dependencies {
        configurations["annotationProcessor"](Libraries.lombok)
        configurations["annotationProcessor"](Libraries.pf4j)

        configurations["compileOnly"](Libraries.oprs_client)
        configurations["compileOnly"](Libraries.oprs_api)

        configurations["compileOnly"](Libraries.guice)
        configurations["compileOnly"](Libraries.javax)
        configurations["compileOnly"](Libraries.lombok)
        configurations["compileOnly"](Libraries.pf4j)
    }

    tasks {
        withType<JavaCompile> {
            options.encoding = "UTF-8"
        }

        withType<AbstractArchiveTask> {
            isPreserveFileTimestamps = false
            isReproducibleFileOrder = true
            dirMode = 493
            fileMode = 420
        }

        withType<Jar> {
            doFirst {
                val d = project.configurations["runtimeClasspath"].map { zipTree(it) }
                from(d)
                duplicatesStrategy = DuplicatesStrategy.EXCLUDE

            }

            doLast {
                println("Copying " + project.buildDir.path + "/libs" + " into " + externalManagerDirectory + "/" + project.name)
                copy {
                    from(project.buildDir.path + "/libs")
                    into(externalManagerDirectory)
                }
            }
        }
    }
}

fun printClasspath(config : Configuration) {
    fun names(set: Set<File>): String {
        return set.joinToString(", ") { it.name }
    }
    println("--------------------------------------------------------")
    println("JAR files on the classpath in order of precedence are:")
    println("Plugins: " + names(config.files))
    println("--------------------------------------------------------")
}

repositories {
    jcenter {
        content {
            excludeGroupByRegex("com\\.openosrs.*")
        }
    }

//        exclusiveContent {
//            forRepository {
//                mavenLocal()
//            }
//            filter {
//                includeGroupByRegex("com\\.openosrs.*")
//            }
//        }
    mavenCentral()
    mavenLocal()
}

val client_dependencies = configurations.create("client_dependencies")
val client = configurations.create("client")

val usage = Attribute.of("org.gradle.usage", Usage::class.java)

configurations.all {
    afterEvaluate {
        if (isCanBeResolved && !attributes.contains(usage)) {
            attributes {
                attribute(usage, project.objects.named(Usage::class.java, "java-runtime"))
            }
        }
    }
}
dependencies {
    attributesSchema {
        attribute(usage)
    }
    client(Libraries.oprs_client)

    client_dependencies(Libraries.OpenOSRSLibraries.jogampJogl)
    client_dependencies(Libraries.OpenOSRSLibraries.jogampJoglLinuxAmd64)
    client_dependencies(Libraries.OpenOSRSLibraries.jogampJoglLinuxI586)
    client_dependencies(Libraries.OpenOSRSLibraries.jogampJoglWindowsI586)
    client_dependencies(Libraries.OpenOSRSLibraries.jogampJoglWindowsAmd64)
    client_dependencies(Libraries.OpenOSRSLibraries.jogampGluegen)
    client_dependencies(Libraries.OpenOSRSLibraries.jogampGluegenLinuxAmd64)
    client_dependencies(Libraries.OpenOSRSLibraries.jogampGluegenLinuxI586)
    client_dependencies(Libraries.OpenOSRSLibraries.jogampGluegenWindowsAmd64)
    client_dependencies(Libraries.OpenOSRSLibraries.jogampGluegenWindowsI586)

    client_dependencies(Libraries.OpenOSRSLibraries.substance)
    client_dependencies(Libraries.OpenOSRSLibraries.trident)
    client_dependencies(Libraries.OpenOSRSLibraries.discord)
    client_dependencies(Libraries.OpenOSRSLibraries.jocl)
    client_dependencies(Libraries.OpenOSRSLibraries.jocl_arm)
    client_dependencies(Libraries.OpenOSRSLibraries.jocl_mac)
}

tasks {

    register<JavaExec>("Client.main()") {
        dependsOn(rootProject.childProjects.map { it.key + ":build" })
        doFirst {
            printClasspath(client)
        }
        group = "run"

        classpath = client
        main = "net.runelite.client.RuneLite"
        jvmArgs = listOf("-ea")
        args = listOf("--developer-mode", "--debug")
    }

    withType<BootstrapTask>() {
        doLast{
            copy{
                from(file("${rootProject.projectDir}/release"))
                into(file("${releaseDir}/release"))
            }
            copy{
                from(file("${rootProject.projectDir}/plugins.json"))
                into(file(releaseDir))
            }
        }
    }
}
