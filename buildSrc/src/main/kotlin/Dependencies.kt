import org.gradle.api.JavaVersion

object Application {
    const val minSdk = 24
    const val targetSdk = 31
    const val compileSdk = 31
    const val jvmTarget = "11"
    const val versionCode = 1
    const val versionName = "1.0.0"

    val targetCompat = JavaVersion.VERSION_11
    val sourceCompat = JavaVersion.VERSION_11
}

object Versions {
    const val Orbit = "4.3.0"
    const val FirebaseBom = "29.0.0"

    object Essential {
        const val Kotlin = "1.5.31"
        const val CoreKtx = "1.7.0"
        const val Coroutines = "1.5.2"
        const val Gradle = "7.1.0-beta03"
        const val GoogleService = "4.3.10"
    }

    object Compose {
        const val Lottie = "4.2.1"
        const val Master = "1.1.0-beta03"
        const val Activity = "1.4.0"
        const val Lifecycle = "2.4.0"
        const val LandscapistCoil = "1.4.3"
        const val Navigation = "2.4.0-alpha09"
        const val ConstraintLayout = "1.0.0-beta01"
    }

    object Util {
        const val Erratum = "1.0.1"
        const val Logeukes = "1.0.1"
        const val Jackson = "2.13.0"
        const val LeakCanary = "2.7"
        const val SecurityCrypto = "1.0.0"
        const val CheckDependencyUpdates = "1.5.0"
    }

    object Network {
        const val OkHttp = "4.9.2"
        const val Retrofit = "2.9.0"
    }

    object Jetpack {
        const val Hilt = "2.40.1"
    }

    object Ui {
        const val Browser = "1.4.0"
        const val Material = "1.4.0"
    }

    object OssLicense {
        const val Master = "17.0.0"
        const val Classpath = "0.10.4"
    }
}

object Dependencies {
    const val Orbit = "org.orbit-mvi:orbit-viewmodel:${Versions.Orbit}"
    const val LandscapistCoil =
        "com.github.skydoves:landscapist-coil:${Versions.Compose.LandscapistCoil}"

    const val Hilt = "com.google.dagger:hilt-android:${Versions.Jetpack.Hilt}"
    const val HiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.Jetpack.Hilt}"

    const val FirebaseBom = "com.google.firebase:firebase-bom:${Versions.FirebaseBom}"

    val Essential = listOf(
        "androidx.core:core-ktx:${Versions.Essential.CoreKtx}",
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Essential.Coroutines}"
    )

    val Ui = listOf(
        "androidx.browser:browser:${Versions.Ui.Browser}",
        "com.google.android.material:material:${Versions.Ui.Material}",
        "com.google.android.gms:play-services-oss-licenses:${Versions.OssLicense.Master}"
    )

    val Jackson = listOf(
        "com.fasterxml.jackson.core:jackson-core:${Versions.Util.Jackson}",
        "com.fasterxml.jackson.core:jackson-databind:${Versions.Util.Jackson}",
        "com.fasterxml.jackson.core:jackson-annotations:${Versions.Util.Jackson}",
        "com.fasterxml.jackson.module:jackson-module-kotlin:${Versions.Util.Jackson}"
    )

    val Retrofit = listOf(
        "com.squareup.retrofit2:retrofit:${Versions.Network.Retrofit}",
        "com.squareup.okhttp3:logging-interceptor:${Versions.Network.OkHttp}",
        "com.squareup.retrofit2:converter-jackson:${Versions.Network.Retrofit}"
    )

    val Util = listOf(
        "io.github.jisungbin:erratum:${Versions.Util.Erratum}",
        "io.github.jisungbin:logeukes:${Versions.Util.Logeukes}",
        "androidx.security:security-crypto:${Versions.Util.SecurityCrypto}"
    )

    val Compose = listOf(
        "androidx.compose.ui:ui:${Versions.Compose.Master}",
        "androidx.compose.ui:ui-tooling:${Versions.Compose.Master}",
        "com.airbnb.android:lottie-compose:${Versions.Compose.Lottie}",
        "androidx.compose.compiler:compiler:${Versions.Compose.Master}",
        "androidx.compose.material:material:${Versions.Compose.Master}",
        "androidx.activity:activity-compose:${Versions.Compose.Activity}",
        "androidx.navigation:navigation-compose:${Versions.Compose.Navigation}",
        "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.Compose.Lifecycle}",
        "androidx.constraintlayout:constraintlayout-compose:${Versions.Compose.ConstraintLayout}"
    )

    val Debug = listOf("com.squareup.leakcanary:leakcanary-android:${Versions.Util.LeakCanary}")

    val Firebase = listOf("com.google.firebase:firebase-firestore-ktx")
}
