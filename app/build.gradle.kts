plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("org.sonarqube") version "6.0.1.5171"
    id("jacoco")
}

sonarqube {
    properties {
        property("sonar.projectKey", "dozyFront")
        property("sonar.projectName", "dozyFront")
        property("sonar.host.url", "http://localhost:9000")
        property("sonar.token", "sqp_c6894e8bdaf25c05889de014c1b4615e5e752c56") // Reemplaza con tu token real
        property("sonar.coverage.jacoco.xmlReportPaths", "build/reports/jacoco/testDebugUnitTestCoverage/testDebugUnitTestCoverage.xml")
        property("sonar.junit.reportPaths", "build/test-results/testDebugUnitTest")
    }
}

jacoco {
    toolVersion = "0.8.10"
}

tasks.register("jacocoTestDebugUnitTestReport", JacocoReport::class) {
    dependsOn("testDebugUnitTest")

    reports {
        xml.required.set(true)
        html.required.set(true)
    }

    val fileFilter = listOf("**/R.class", "**/R$*.class", "**/BuildConfig.*", "**/Manifest*.*")

    classDirectories.setFrom(
        fileTree("${buildDir}/intermediates/javac/debug") {
            exclude(fileFilter)
        }
    )

    sourceDirectories.setFrom(files("src/main/java"))
    executionData.setFrom(fileTree(buildDir) {
        include("**/jacoco/testDebugUnitTest.exec")
    })
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

    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.activity:activity:1.10.1")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")

    // Test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.13.0"))
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-auth")

    // Retrofit + Gson
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.code.gson:gson:2.10.1")
}
