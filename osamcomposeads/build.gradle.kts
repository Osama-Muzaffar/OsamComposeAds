import org.apache.tools.ant.util.JavaEnvUtils.VERSION_11

plugins {
//    alias(libs.plugins.android.library)
    id("com.android.library")
    alias(libs.plugins.jetbrains.kotlin.android)
    id("maven-publish")
}

android {
    namespace = "com.compose.osamcomposeads"
    compileSdk = 34

    defaultConfig {
//        applicationId = "com.compose.osamcomposeads"
        minSdk = 24
        targetSdk = 34
//        versionCode = 1
//        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
//publishing {
//    publications {
//        create<MavenPublication>("mavenJava") {
////            from(components["release"])
//            groupId = "com.github.Osama-Muzaffar"
//            artifactId = "osamcomposeads"
//            version = "0.0.11"
//        }
//    }
//}
publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.github.Osama-Muzaffar"
            artifactId = "osamcomposeads"
            version = "1.0.0"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}


dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.play.services.ads.lite)
    implementation(libs.androidx.cardview)
    implementation(libs.androidx.constraintlayout)
    implementation (libs.shimmer)
    implementation ("com.airbnb.android:lottie-compose:4.0.0")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}