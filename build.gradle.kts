plugins {
    scala
    application
    id("net.researchgate.release") version "2.8.1"
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
    testRuntimeOnly("org.scala-lang.modules:scala-xml_$scalaCompatVersion:1.3.0")
}
