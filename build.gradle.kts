buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath(ClassPaths.sqlDelight)
        classpath(ClassPaths.kotlin)
        classpath(ClassPaths.gradle)
        classpath(ClassPaths.nav)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}