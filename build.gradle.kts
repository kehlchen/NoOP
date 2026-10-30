plugins {
    java
}

group = "info.plugin.minecraft"
version = "1.0"

repositories {
    mavenCentral()

    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:26.1.1.build.29-alpha")
    implementation("com.squareup.okhttp3:okhttp:5.1.0")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}