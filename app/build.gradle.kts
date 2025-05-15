import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services") version "4.4.2"
}

val localProps = Properties()
val localPropsFile = rootProject.file("local.properties")
if (localPropsFile.exists()) {
    localProps.load(localPropsFile.inputStream())
}
val myApiKey = localProps.getProperty("MY_API_KEY") ?: "MISSING_API_KEY"

android {
    namespace = "com.example.planitout"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.planitout"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "MY_API_KEY", "\"$myApiKey\"")
        resValue("string", "google_maps_key", myApiKey)
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

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
        isCoreLibraryDesugaringEnabled = true
    }
}

dependencies {
    // AndroidX and Material Design
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.recyclerview)
    implementation(libs.android.gif.drawable)
    implementation(libs.core.splashscreen)
    implementation(libs.places)
    implementation(libs.firebase.storage)
    implementation(libs.glide)
    implementation(libs.androidx.room.compiler)
    annotationProcessor(libs.compiler)
    implementation(libs.flexbox)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Room Database
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)

    // Lifecycle (ViewModel/LiveData and Swipe Refresh)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.livedata)
    implementation(libs.swiperefreshlayout)

    // Retrofit for API calls
    implementation(libs.retrofit2.retrofit)

    // Google Play Services (Maps and Location)
    implementation(libs.play.services.maps)
    implementation(libs.play.services.location)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.database)
    implementation(libs.firebase.analytics)

    // Calendar
    implementation(libs.view)
    implementation(libs.compose)
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    configurations.all {
        resolutionStrategy {
            force("org.jetbrains:annotations:23.0.0")
            eachDependency {
                if (requested.group == "com.intellij" && requested.name == "annotations") {
                    useTarget("org.jetbrains:annotations:23.0.0")
                }
            }
        }
    }
}
