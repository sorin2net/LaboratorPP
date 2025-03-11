plugins {
    kotlin("jvm") version "1.9.0"
    application
}

group = "com.example"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    // Dependențele pentru Log4j2
    implementation("org.apache.logging.log4j:log4j-api:2.20.0")
    implementation("org.apache.logging.log4j:log4j-core:2.20.0")

    // Alte dependențe (cum ar fi Apache POI)
    implementation("org.apache.poi:poi-ooxml:5.2.3")
    implementation("org.apache.poi:poi:5.2.3")
}

kotlin {
    jvmToolchain(17) // Setează target-ul JVM la 17 (valid)
}

application {
    mainClass.set("MainKt")
}
