import org.gradle.api.JavaVersion

/**
 * TODO: Add class header description
 * Created by kurniawanrizzki on 11/11/20.
 */
object Config {
    var minSdkVersion = 19
    var compileSdkVersion = 30
    var targetSdkVersion = 30
    val buildToolsVersion = "30.0.0"
    val javaVersion = JavaVersion.VERSION_1_8
    val jvmTargetVersion = "1.8"
}

object Versions {
    val vKotlin = "1.4.10"
    val vBuildGradle = "4.1.0"
    val vCore = "1.2.0"
    val vMaterial = "1.2.1"
    val vConstraintLayout = "2.0.2"
    val vRecyclerView = "1.1.0"
    val vLottie = "3.5.0"
    val vRetrofit = "2.9.0"
    val vOkHttpClient = "4.9.0"
    val vPicasso = "2.71828"
    val vIntuit = "1.0.6"

    val vJunit = "4.+"
    val vJunitExt = "1.1.2"
    val vEspressoCore = "3.3.0"
}

object Deps {
    val buildGradle = "com.android.tools.build:gradle:${Versions.vBuildGradle}"
    val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.vKotlin}"
    val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.vKotlin}"
    val androidCore = "androidx.core:core-ktx:${Versions.vCore}"
    val appCompat = "androidx.appcompat:appcompat:${Versions.vCore}"
    val material = "com.google.android.material:material:${Versions.vMaterial}"
    val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.vConstraintLayout}"
    val recyclerView = "androidx.recyclerview:recyclerview:${Versions.vRecyclerView}"
    val lottie = "com.airbnb.android:lottie:${Versions.vLottie}"
    val sdp = "com.intuit.sdp:sdp-android:${Versions.vIntuit}"
    val ssp = "com.intuit.ssp:ssp-android:${Versions.vIntuit}"

    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.vRetrofit}"
    val okHttpClient = "com.squareup.okhttp3:okhttp:${Versions.vOkHttpClient}"
    val gson = "com.squareup.retrofit2:converter-gson:${Versions.vRetrofit}"
    val picasso = "com.squareup.picasso:picasso:${Versions.vPicasso}"

    val junit = "junit:junit:${Versions.vJunit}"
    val junitExt = "androidx.test.ext:junit:${Versions.vJunitExt}"
    val espressoCore = "androidx.test.espresso:espresso-core:${Versions.vEspressoCore}"
}