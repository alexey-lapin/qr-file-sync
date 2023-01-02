plugins {
    id("java")
}

dependencies {
    implementation("commons-codec:commons-codec:1.15")
    implementation("com.google.zxing:core:3.5.1")
    implementation("com.google.zxing:javase:3.5.1")

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
    testImplementation("org.assertj:assertj-core:3.23.1")
    testImplementation("com.google.jimfs:jimfs:1.2")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
