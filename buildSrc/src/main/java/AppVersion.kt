/**
 * Created by Salih Karakaşlı on 20.05.2022.
 */

object AppVersion {
    private const val versionMajor = 0
    private const val versionMinor = 0
    private const val versionPatch = 1
    private const val versionCodePrefix = 0

    fun getAppVersionCode(): String =
        (versionMajor * 1000000 + versionMinor * 10000 + versionPatch * 100 + versionCodePrefix).toString()

    fun getAppVersionName(): String =
        "${versionMajor}.${versionMinor}.${versionPatch}"
}