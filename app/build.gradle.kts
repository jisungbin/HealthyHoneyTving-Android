plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
    id("dagger.hilt.android.plugin")
    id("com.google.android.gms.oss-licenses-plugin")
    id("name.remal.check-dependency-updates") version Versions.Util.CheckDependencyUpdates
}

android {
    compileSdk = Application.compileSdk

    defaultConfig {
        minSdk = Application.minSdk
        targetSdk = Application.targetSdk
        versionCode = Application.versionCode
        versionName = Application.versionName
        multiDexEnabled = true
        setProperty("archivesBaseName", "$versionName ($versionCode)")

        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("long", "TIMESTAMP", "${System.currentTimeMillis()}L")
    }

    buildFeatures {
        compose = true
    }

    buildTypes {
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
        }
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.Compose.Master
    }

    sourceSets {
        getByName("main").run {
            java.srcDirs("src/main/kotlin")
        }
    }

    compileOptions {
        sourceCompatibility = Application.sourceCompat
        targetCompatibility = Application.targetCompat
    }

    kotlinOptions {
        jvmTarget = Application.jvmTarget
    }
}

dependencies {
    implementation(Dependencies.Hilt)
    implementation(Dependencies.Orbit)
    implementation(Dependencies.LandscapistCoil) {
        exclude(group = "androidx.appcompat", module = "appcompat")
        exclude(group = "androidx.appcompat", module = "appcompat-resources")
    }

    implementation(platform(Dependencies.FirebaseBom))

    Dependencies.Ui.forEach(::implementation)
    Dependencies.Util.forEach(::implementation)
    Dependencies.Jackson.forEach(::implementation)
    Dependencies.Compose.forEach(::implementation)
    Dependencies.Retrofit.forEach(::implementation)
    Dependencies.Firebase.forEach(::implementation)

    Dependencies.Debug.forEach(::debugImplementation)

    kapt(Dependencies.HiltCompiler) // TODO: ksp
}
