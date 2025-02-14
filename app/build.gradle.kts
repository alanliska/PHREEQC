plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "cz.jh.phreeqc"
    compileSdk = 35

    defaultConfig {
        applicationId = "cz.jh.phreeqc"
        minSdk = 24
        targetSdk = 35
        versionCode = 8
        versionName = "1.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    packaging {
        jniLibs {
            useLegacyPackaging = true
        }
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("com.github.Bhuvaneshw:acpdfview:1.1.3")
    implementation("com.jjoe64:graphview:4.2.2")
    implementation("com.jrummyapps:android-shell:1.0.1")
}