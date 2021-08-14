plugins {
    application
    id("com.github.ben-manes.versions")
    id("com.github.johnrengelman.shadow")
}

repositories {
    mavenCentral()
}

dependencies {
    annotationProcessor("org.projectlombok:lombok:1.18.20")
    compileOnly("org.projectlombok:lombok:1.18.20")

    implementation("commons-codec:commons-codec:1.15")
    implementation("com.google.zxing:core:3.4.1")
    implementation("com.google.zxing:javase:3.4.1")

    testImplementation("org.junit.jupiter:junit-jupiter:5.7.2")
}

application {
    mainClass.set("com.github.alexeylapin.Main")
}

tasks.test {
    useJUnitPlatform()
}

tasks {
    dependencyUpdates {
        checkConstraints = true
        resolutionStrategy {
            componentSelection {
                all {
                    val rejected = listOf("alpha", "beta", "rc", "cr", "m", "preview", "b", "ea")
                        .map { qualifier -> Regex("(?i).*[.-]$qualifier[.\\d-+]*") }
                        .any { it.matches(candidate.version) }
                    if (rejected) {
                        reject("Release candidate")
                    }
                }
            }
        }
    }
}