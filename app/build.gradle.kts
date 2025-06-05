plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("org.sonarqube") version "6.0.1.5171"
}
sonarqube {
    properties {
        property("sonar.projectKey", "Dozy")
        property("sonar.host.url", "http://localhost:9000")
        property("sonar.token", "sqp_a309fc560b8d39b537ba6ede60293b33cafbd09d")
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
    implementation("androidx.activity:activity:1.10.1")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    implementation(platform("com.google.firebase:firebase-bom:33.13.0"))
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-auth")
}