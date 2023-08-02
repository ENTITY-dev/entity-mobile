plugins {
  alias(libs.plugins.multiplatform)
  alias(libs.plugins.compose)
  alias(libs.plugins.cocoapods)
  alias(libs.plugins.android.application)
  alias(libs.plugins.libres)
  alias(libs.plugins.buildConfig)
  alias(libs.plugins.kotlinx.serialization)
  alias(libs.plugins.sqlDelight)
  alias(libs.plugins.apollo)
}

kotlin {
  androidTarget {
    compilations.all {
      kotlinOptions {
        jvmTarget = "1.8"
      }
    }
  }

  iosX64()
  iosArm64()
  iosSimulatorArm64()

  cocoapods {
    version = "1.0.0"
    summary = "Compose application framework"
    homepage = "empty"
    ios.deploymentTarget = "14.1"
    podfile = project.file("../iosApp/Podfile")
    pod("Sentry", "~> 8.4.0")
    framework {
      baseName = "ComposeApp"
      isStatic = true
    }
  }

  sourceSets {
    val commonMain by getting {
      dependencies {
        api(libs.sentry)
        api(libs.composeImageLoader)

        implementation(compose.runtime)
        implementation(compose.foundation)
        implementation(compose.material)
        implementation(libs.libres)
        implementation(libs.voyager.navigator)
        implementation(libs.voyager.transitions)
        implementation(libs.voyager.bottom)
        implementation(libs.voyager.tab)
        implementation(libs.napier)
        implementation(libs.kotlinx.coroutines.core)
        implementation(libs.ktor.core)
        implementation(libs.ktor.client.content.negotiation)
        implementation(libs.ktor.serialization)
        implementation(libs.ktor.client.logging)
        implementation(libs.ktor.client.auth)
        implementation(libs.composeIcons.featherIcons)
        implementation(libs.kotlinx.serialization.json)
        implementation(libs.kotlinx.datetime)
        implementation(libs.multiplatformSettings)
        implementation(libs.multiplatformSettings.serialization)
        implementation(libs.multiplatformSettings.noArg)
        implementation(libs.koin.core)
        implementation(libs.kstore)
        implementation(libs.apollo.runtime)
      }
    }

    val commonTest by getting {
      dependencies {
        implementation(kotlin("test"))
      }
    }

    val androidMain by getting {
      dependencies {
        implementation(libs.androidx.appcompat)
        implementation(libs.androidx.activityCompose)
        implementation(libs.compose.uitooling)
        implementation(libs.kotlinx.coroutines.android)
        implementation(libs.ktor.client.okhttp)
        implementation(libs.sqlDelight.driver.android)
        implementation(libs.koin.android)
        implementation(libs.accompanist.systemuicontroller)
      }
    }

    val iosX64Main by getting
    val iosArm64Main by getting
    val iosSimulatorArm64Main by getting
    val iosMain by creating {
      dependsOn(commonMain)
      iosX64Main.dependsOn(this)
      iosArm64Main.dependsOn(this)
      iosSimulatorArm64Main.dependsOn(this)
      dependencies {
        implementation(libs.ktor.client.darwin)
        implementation(libs.sqlDelight.driver.native)
      }
    }

    val iosX64Test by getting
    val iosArm64Test by getting
    val iosSimulatorArm64Test by getting
    val iosTest by creating {
      dependsOn(commonTest)
      iosX64Test.dependsOn(this)
      iosArm64Test.dependsOn(this)
      iosSimulatorArm64Test.dependsOn(this)
    }
  }
}

android {
  namespace = "com.entity.app"
  compileSdk = 33

  defaultConfig {
    minSdk = 24
    targetSdk = 33

    applicationId = "com.entity.app.androidApp"
    versionCode = 1
    versionName = "0.0.1"
  }
  sourceSets["main"].apply {
    manifest.srcFile("src/androidMain/AndroidManifest.xml")
    res.srcDirs("src/androidMain/resources")
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  packagingOptions {
    resources.excludes.add("META-INF/**")
  }
}


libres {
  generatedClassName = "EntityRes"
  baseLocaleLanguageCode = "en"
  generateNamedArguments = true
}

buildConfig {
  // BuildConfig configuration here.
  // https://github.com/gmazzo/gradle-buildconfig-plugin#usage-in-kts
}
sqldelight {
  databases {
    create("MyDatabase") {
      // Database configuration here.
      // https://cashapp.github.io/sqldelight
      packageName.set("com.entity.app.db")
    }
  }
}

apollo {
  service("api") {
    // GraphQL configuration here.
    // https://www.apollographql.com/docs/kotlin/advanced/plugin-configuration/
    packageName.set("com.entity.app.graphql")
  }
}
