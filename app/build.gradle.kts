import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.gms)
    alias(libs.plugins.firebase.crashlytics)
}

val localProperties = Properties()
localProperties.load(project.rootProject.file("local.properties").inputStream())

android {
    namespace = "com.maum.note"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.maum.note"
        minSdk = 26
        targetSdk = 35
        versionCode = 6
        versionName = "1.0.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            buildConfigField("String", "GPT_API_URL", localProperties["gpt.api.url.dev"].toString())
            buildConfigField("String", "GPT_API_KEY", localProperties["gpt.api.key.dev"].toString())
            buildConfigField("String", "CLARITY_PROJECT_ID", localProperties["clarity.project.id.dev"].toString())
            buildConfigField("String", "SUPABASE_URL", localProperties["supabase.url.dev"].toString())
            buildConfigField("String", "SUPABASE_KEY", localProperties["supabase.key.dev"].toString())
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "GPT_API_URL", localProperties["gpt.api.url.prod"].toString())
            buildConfigField("String", "GPT_API_KEY", localProperties["gpt.api.key.prod"].toString())
            buildConfigField("String", "CLARITY_PROJECT_ID", localProperties["clarity.project.id.prod"].toString())
            buildConfigField("String", "SUPABASE_URL", localProperties["supabase.url.prod"].toString())
            buildConfigField("String", "SUPABASE_KEY", localProperties["supabase.key.prod"].toString())
        }
    }

    flavorDimensions += "environment"
    productFlavors {
        create("dev") {
            dimension = "environment"
            applicationIdSuffix = ".dev"
        }
        create("prod") {
            dimension = "environment"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    room {
        schemaDirectory("$projectDir/schemas")
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.androidx.core.splashscreen)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    // hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation)
    implementation(libs.javax.inject)

    // datastore
    implementation(libs.androidx.datastore)
    implementation(libs.androidx.datastore.core)

    // Paging
    implementation(libs.paging.runtime)
    implementation(libs.paging.common)
    implementation(libs.paging.compose)

    // Room
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
    ksp(libs.androidx.room.compiler)
    testImplementation(libs.room.testing)

    // firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.gms.play.services.auth)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.appcheck.playintegrity)
    implementation(libs.firebase.messaging)

    // retrofit
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.gson.converter)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)

    // moshi
    implementation(libs.moshi.kotlin)

    // lottie
    implementation(libs.lottie)

    // clarity
    implementation(libs.clarity)

    // supabase
    implementation(libs.supabase.postgrest.kt)
    implementation(libs.supabase.auth.kt)


    // ktor
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.utils)
}