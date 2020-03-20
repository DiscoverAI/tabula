plugins {
    scala
    application
    id("net.researchgate.release") version "2.8.1"
    id("com.github.maiflai.scalatest") version "0.26"
}

repositories {
    jcenter()
}

val scalaCompatVersion = "2.12"
val scalaVersion = "$scalaCompatVersion.11"

version = "0.2.2-SNAPSHOT"
group = "com.github.discoverai"

application {
    mainClassName = "com.github.discoverai.tabula.Tabula"
}

dependencies {
    implementation("org.scala-lang:scala-library:$scalaVersion")
    testImplementation("org.scalatest:scalatest_$scalaCompatVersion:3.1.1")
    testRuntimeOnly("com.vladsch.flexmark:flexmark-all:0.35.10")
}
