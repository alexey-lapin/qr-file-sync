plugins {
    id("java")
}

dependencies {
    implementation(project(":core"))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
