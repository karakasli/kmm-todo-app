/**
 * Created by Salih Karakaşlı on 20.05.2022.
 */

object Versions {
    const val kotlin = "1.7.0"
    const val appCompat = "1.4.1"
    const val material = "1.5.0"
    const val constraintLayout = "2.1.3"
    const val activityKtx = "1.4.0"
    const val fragmentKtx = "1.4.1"
    const val navigation = "2.5.0-alpha03"
    const val lifecycle = "2.4.1"
    const val coroutines = "1.6.0"
    const val splashScreen = "1.0.0-beta02"
    const val koin = "3.2.0"
    const val materialDialogs = "3.3.0"
    const val sqlDelight = "1.5.3"
}

object Dependencies {

    const val kotlin = "androidx.core:core-ktx:${Versions.kotlin}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val activityKtx = "androidx.activity:activity-ktx:${Versions.activityKtx}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragmentKtx}"
    const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigationUI = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    const val splashScreen = "androidx.core:core-splashscreen:${Versions.splashScreen}"

    /**
     * AndroidX LifeCycle Dependencies
     */
    const val lifecycleViewmodelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val lifecycleLivedataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val lifecycleLivedataCoreKtx = "androidx.lifecycle:lifecycle-livedata-core-ktx:${Versions.lifecycle}"
    const val lifecycleCommonJava8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"
    const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"

    const val material = "com.google.android.material:material:${Versions.material}"

    /**
     * Utilities from 3rd parties
     */
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val materialDialogs = "com.afollestad.material-dialogs:core:${Versions.materialDialogs}"

    /**
     * Koin
     */
    const val koinCore = "io.insert-koin:koin-core:${Versions.koin}"
    const val koinAndroid = "io.insert-koin:koin-android:${Versions.koin}"

    const val sqlDelightAndroid = "com.squareup.sqldelight:android-driver:${Versions.sqlDelight}"
    const val sqlDelightNative = "com.squareup.sqldelight:native-driver:${Versions.sqlDelight}"


}