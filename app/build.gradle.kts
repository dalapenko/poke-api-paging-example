plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kapt)
    alias(libs.plugins.apollo)
}

android {
    namespace = "tech.dalapenko.pokeapipagingexample"
    compileSdk = 34

    defaultConfig {
        applicationId = "tech.dalapenko.pokeapipagingexample"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

apollo {
    val graphQLDir = "src/main/java/tech/dalapenko/pokeapipagingexample/core/network/graphql"
    val graphQLSchemaPath = "$graphQLDir/schema.graphqls"

    service("service") {
        packageName.set("tech.dalapenko.pokeapipagingexample")
        schemaFile.set(file(graphQLSchemaPath))
        srcDir(file(graphQLDir))
    }
}

dependencies {

    implementation(libs.bundles.app.implementation)
    kapt(libs.bundles.app.kapt)
}