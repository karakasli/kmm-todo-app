plugins {
    id("com.android.application")
    kotlin("android")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdk = ConfigData.compileSdk
    defaultConfig {
        applicationId = ConfigData.appId
        minSdk = ConfigData.minSdk
        targetSdk = ConfigData.targetSdk
        versionCode = ConfigData.versionCode.toInt()
        versionName = AppVersion.getAppVersionName()
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(Dependencies.material)
    implementation(Dependencies.appCompat)
    implementation(Dependencies.constraintLayout)
    implementation(Dependencies.activityKtx)
    implementation(Dependencies.fragmentKtx)
    implementation(Dependencies.navigationFragment)
    implementation(Dependencies.navigationUI)

    implementation(Dependencies.lifecycleViewmodelKtx)
    implementation(Dependencies.lifecycleLivedataKtx)
    implementation(Dependencies.lifecycleLivedataCoreKtx)
    implementation(Dependencies.lifecycleCommonJava8)
    implementation(Dependencies.lifecycleRuntime)
    implementation(Dependencies.coroutinesAndroid)
    implementation(Dependencies.coroutinesCore)

    implementation(Dependencies.koinCore)
    implementation(Dependencies.koinAndroid)

    implementation(Dependencies.splashScreen)
    implementation(Dependencies.materialDialogs)

}