plugins {
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.google.ksp.api)
    implementation(libs.squareup.kotlinpoet)
    implementation(libs.squareup.kotlinpoet.ksp)
}