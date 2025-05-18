plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("org.sonarqube") version "3.5.0.2730"
}
sonarqube {
    properties {
        property("sonar.projectKey", "Dozy")
        property("sonar.host.url", "http://localhost:9000")
        property("sonar.login", "sqp_67f07fb96110dffc08bfa590effe31802a2caa7f")
    }
}

android {
    namespace = "com.example.dozy"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.dozy"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    implementation(platform("com.google.firebase:firebase-bom:33.13.0"))
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-auth")
}