plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("com.squareup.sqldelight")
}

kotlin {
    android()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Dependencies.koinCore)

            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Dependencies.sqlDelightAndroid)
            }
        }

        val androidTest by getting
        val iosX64Main by getting {
            dependencies {
                implementation(Dependencies.sqlDelightNative)
            }
        }
        val iosArm64Main by getting {
            dependencies {
                implementation(Dependencies.sqlDelightNative)
            }
        }

        val iosSimulatorArm64Main by getting {
            dependencies {
                implementation(Dependencies.sqlDelightNative)
            }
        }

        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation(Dependencies.sqlDelightNative)
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    compileSdk = 32
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
        targetSdk = 32
    }
}

sqldelight {
    database(name = "AppDatabase") {
        packageName = "com.karakasli.kmm_todo_app.db"
        sourceFolders = listOf("sqldelight")
    }
}