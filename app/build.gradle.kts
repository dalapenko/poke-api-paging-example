plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kapt)
    alias(libs.plugins.apollo)
}

android {
    namespace = "tech.dalapenko.pokemonpagingexample"
    compileSdk = 34

    defaultConfig {
        applicationId = "tech.dalapenko.pokemonpagingexample"
        minSdk = 27
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

apollo {
    service("service") {
        packageName.set("tech.dalapenko.pokemonpagingexample")
        schemaFile.set(file("src/main/java/tech/dalapenko/pokemonpagingexample/core/network/graphql/schema.json"))
        srcDir(file("src/main/java/tech/dalapenko/pokemonpagingexample/core/network/graphql"))
        generateApolloMetadata.set(true)
    }
}

dependencies {

    implementation(libs.bundles.app.implementation)
    kapt(libs.bundles.app.kapt)
}
