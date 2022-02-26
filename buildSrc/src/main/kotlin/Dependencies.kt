/*
 * Copyright (c) 2019 Owain van Brakel <https://github.com/Owain94>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

object ProjectVersions {
    const val openosrsVersion = "latest.release"
    const val apiVersion = "^1.0.0"
}

object Libraries {
    private object Versions {
        const val guice = "5.0.1"
        const val javax = "1.3.2"
        const val lombok = "1.18.20"
        const val pf4j = "3.6.0"
        const val slf4j = "1.7.32"

        const val annotations = "18.0.0"
        const val antlr = "4.7.2"
        const val apacheCommonsCompress = "1.19"
        const val apacheCommonsCsv = "1.7"
        const val apacheCommonsText = "1.8"
        const val asm = "7.2"
        const val commonsCli = "1.4"
        const val discord = "1.1"
        const val fernflower = "07082019"
        const val findbugs = "3.0.2"
        const val gson = "2.8.6"
        const val guava = "28.1-jre"
        const val h2 = "1.4.200"
        const val hamcrest = "2.2"
        const val javaxInject = "1"
        const val jedis = "3.1.0"
        const val jna = "5.5.0"
        const val jogamp = "2.3.2"
        const val jopt = "5.0.4"
        const val jooq = "3.12.3"
        const val junit = "4.12"
        const val jupiter = "5.6.0-M1"
        const val logback = "1.2.3"
        const val mapstruct = "1.3.1.Final"
        const val mariadbJdbc = "2.5.1"
        const val mavenPluginAnnotations = "3.6.0"
        const val mavenPluginApi = "3.6.2"
        const val minio = "6.0.11"
        const val mockito = "3.1.0"
        const val mongodbDriverSync = "3.11.2"
        const val mysqlConnectorJava = "8.0.18"
        const val naturalMouse = "2.0.2"
        const val netty = "4.1.43.Final"
        const val okhttp3 = "4.2.2"
        const val orangeExtensions = "1.0"
        const val petitparser = "2.2.0"
        const val plexus = "3.3.0"
        const val rxjava = "2.2.14"
        const val rxrelay = "2.1.1"
        const val scribejava = "6.9.0"
        const val sisu = "0.3.4"
        const val sentry = "1.7.28"
        const val springJdbc = "5.2.1.RELEASE"
        const val springboot = "2.2.1.RELEASE"
        const val sql2o = "1.6.0"
        const val substance = "8.0.02"
        const val trident = "1.5.00"
        const val runelite = "latest.release"
        const val jcodec = "0.2.5"
        const val microsoft_alm = "0.6.4"
    }

    object OpenOSRSLibraries {
        const val runelite_http_api = "net.runelite:http-api:latest.release"
        const val runelite_jshell = "net.runelite:jshell:latest.release"
        const val logback = "ch.qos.logback:logback-classic:${Versions.logback}"
        const val gson = "com.google.code.gson:gson:${Versions.gson}"
        const val guava = "com.google.guava:guava:${Versions.guava}"
        const val guice = "com.google.inject:guice:${Versions.guice}:no_aop"
        const val discord = "net.runelite:discord:${Versions.discord}"
        const val jna = "net.java.dev.jna:jna:${Versions.jna}"
        const val jnaPlatform = "net.java.dev.jna:jna-platform:${Versions.jna}"
        const val rxjava = "io.reactivex.rxjava2:rxjava:${Versions.rxjava}"
        const val rxrelay = "com.jakewharton.rxrelay2:rxrelay:${Versions.rxrelay}"
        const val okhttp3 = "com.squareup.okhttp3:okhttp:${Versions.okhttp3}"
        const val substance = "net.runelite.pushingpixels:substance:${Versions.substance}"
        const val trident = "net.runelite.pushingpixels:trident:${Versions.trident}"
        const val jopt = "net.sf.jopt-simple:jopt-simple:${Versions.jopt}"

        const val jocl = "net.runelite.jocl:jocl:1.0"
        const val jocl_mac = "net.runelite.jocl:jocl:1.0:macos-x64"
        const val jocl_arm = "net.runelite.jocl:jocl:1.0:macos-arm64"

        const val jogampJogl = "org.jogamp.jogl:jogl-all:${Versions.jogamp}"
        const val jogampGluegen = "org.jogamp.gluegen:gluegen-rt:${Versions.jogamp}"
        const val jogampGluegenLinuxAmd64 = "org.jogamp.gluegen:gluegen-rt:${Versions.jogamp}:natives-linux-amd64"
        const val jogampGluegenLinuxI586 = "org.jogamp.gluegen:gluegen-rt:${Versions.jogamp}:natives-linux-i586"
        const val jogampGluegenWindowsAmd64 = "org.jogamp.gluegen:gluegen-rt:${Versions.jogamp}:natives-windows-amd64"
        const val jogampGluegenWindowsI586 = "org.jogamp.gluegen:gluegen-rt:${Versions.jogamp}:natives-windows-i586"
        const val jogampJoglLinuxAmd64 = "org.jogamp.jogl:jogl-all:${Versions.jogamp}:natives-linux-amd64"
        const val jogampJoglLinuxI586 = "org.jogamp.jogl:jogl-all:${Versions.jogamp}:natives-linux-i586"
        const val jogampJoglWindowsAmd64 = "org.jogamp.jogl:jogl-all:${Versions.jogamp}:natives-windows-amd64"
        const val jogampJoglWindowsI586 = "org.jogamp.jogl:jogl-all:${Versions.jogamp}:natives-windows-i586"

        const val pf4j = "org.pf4j:pf4j:3.6.0"
        const val pf4j_update = "org.pf4j:pf4j-update:2.3.0"
//        implementation(group = "org.jgroups", name = "jgroups", version = "5.1.9.Final")
//        implementation(group = "net.sf.jopt-simple", name = "jopt-simple", version = "5.0.4")
//        implementation(group = "org.madlonkay", name = "desktopsupport", version = "0.6.0")
//        implementation(group = "org.apache.commons", name = "commons-text", version = "1.9")
//        implementation(group = "org.apache.commons", name = "commons-csv", version = "1.9.0")
//        implementation(group = "commons-io", name = "commons-io", version = "2.8.0")
//        implementation(group = "org.jetbrains", name = "annotations", version = "22.0.0")
//        implementation(group = "com.github.zafarkhaja", name = "java-semver", version = "0.9.0")

    }

    const val oprs_client = "com.openosrs:runelite-client:${ProjectVersions.openosrsVersion}"
    const val oprs_api = "com.openosrs:runelite-api:${ProjectVersions.openosrsVersion}"
    const val microsoft_alm_common = "com.microsoft.alm:auth-common:${Versions.microsoft_alm}"
    const val microsoft_alm_storage = "com.microsoft.alm:auth-secure-storage:${Versions.microsoft_alm}"
    const val jcodec = "org.jcodec:jcodec:${Versions.jcodec}"
    const val jcodec_javase = "org.jcodec:jcodec-javase:${Versions.jcodec}"
    const val guice = "com.google.inject:guice:${Versions.guice}"
    const val javax = "javax.annotation:javax.annotation-api:${Versions.javax}"
    const val lombok = "org.projectlombok:lombok:${Versions.lombok}"
    const val pf4j = "org.pf4j:pf4j:${Versions.pf4j}"
    const val slf4j = "org.slf4j:slf4j-api:${Versions.slf4j}"

}
