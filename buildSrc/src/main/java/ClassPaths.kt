/**
 * Created by Salih Karakaşlı on 20.05.2022.
 */

object ClassPathsVersions {
    const val kotlin = "1.6.21"
    const val gradle = "7.1.3"
    const val navigation = "2.4.1"
    const val sqlDelight = "1.5.3"
}

object ClassPaths {
    const val gradle = "com.android.tools.build:gradle:${ClassPathsVersions.gradle}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${ClassPathsVersions.kotlin}"
    const val nav = "androidx.navigation:navigation-safe-args-gradle-plugin:${ClassPathsVersions.navigation}"
    const val sqlDelight = "com.squareup.sqldelight:gradle-plugin:${ClassPathsVersions.sqlDelight}"
}