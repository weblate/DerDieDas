import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
}

val detectedLocales = detectLocales()
val langsListString = "{${detectedLocales.sorted().joinToString(",") { "\"$it\"" }}}"

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
    arg("room.incremental", "true")
    arg("room.generateKotlin", "true")
}

android {
    namespace = "com.machiav3lli.derdiedas"

    compileSdk = 36
    defaultConfig {
        applicationId = "com.machiav3lli.derdiedas"
        minSdk = 24
        targetSdk = 36
        versionCode = 2200
        versionName = "2.2.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        androidResources.localeFilters.addAll(detectedLocales)
    }

    applicationVariants.all {
        val variant = this
        outputs.all {
            (this as BaseVariantOutputImpl).outputFileName =
                "DerDieDas_${variant.versionName}_${variant.buildType.name}.apk"
        }
    }

    buildTypes {
        named("release") {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            isMinifyEnabled = true
        }
        named("debug") {
            applicationIdSuffix = ".debug"
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    dependenciesInfo {
        includeInApk = false
        includeInBundle = false
    }
    packaging {
        kotlinExtension.sourceSets.all {
            languageSettings.enableLanguageFeature("ExplicitBackingFields")
        }
    }

    val generateLocales by tasks.registering(GenerateLocalesTask::class) {
        resDir.set(project.layout.projectDirectory.dir("src/main/res"))
        outputDir.set(project.layout.buildDirectory.dir("generated/source/locales/kotlin/main"))
    }

    androidComponents.onVariants { variant ->
        variant.sources.kotlin!!.addGeneratedSourceDirectory(
            generateLocales,
            GenerateLocalesTask::outputDir
        )

        tasks.withType<KotlinCompile> {
            dependsOn(generateLocales)
        }
    }
}

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.ksp)

    //Libs
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    // Koin
    api(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    implementation(libs.koin.startup)
    implementation(libs.koin.annotations)
    ksp(libs.koin.compiler)

    // UI
    implementation(libs.androidx.preference.ktx)
    implementation(libs.datastore.preferences)
    implementation(libs.material)
    api(platform(libs.compose.bom))
    implementation(libs.compose.runtime)
    implementation(libs.compose.ui)
    implementation(libs.compose.foundation)
    implementation(libs.compose.foundation.layout)
    implementation(libs.compose.material3)
    implementation(libs.compose.animation)
    implementation(libs.compose.navigation3)
    implementation(libs.compose.navigation3.ui)

    // Test
    implementation(libs.androidx.test.junit)
    implementation(libs.androidx.test.espresso.core)
}

fun detectLocales(): Set<String> {
    // in /res are (almost) all languages that have a translated string is saved. this is safer and saves some time
    val langsList = mutableSetOf<String>()
    fileTree("src/main/res").visit {
        if (this.file.name == "strings.xml" && this.file.readText().contains("<string")) {
            val languageCode = this.file.parentFile?.name?.removePrefix("values-")?.let {
                if (it == "values") "en" else it
            }
            languageCode?.let { langsList.add(it) }
        }
    }
    return langsList
}

abstract class GenerateLocalesTask : DefaultTask() {
    @get:InputFiles
    @get:PathSensitive(PathSensitivity.RELATIVE)
    @get:IgnoreEmptyDirectories
    abstract val resDir: DirectoryProperty

    @get:OutputDirectory
    abstract val outputDir: DirectoryProperty

    init {
        group = "build"
        description = "Generates DetectedLocales.kt from res directories"
    }

    @TaskAction
    fun generate() {
        val detectedLocales = mutableSetOf<String>()
        resDir.get().asFileTree.visit {
            if (file.isFile && file.name == "strings.xml" && file.readText().contains("<string")) {
                val languageCode = file.parentFile?.name?.removePrefix("values-")?.let {
                    if (it == "values") "en" else it
                }
                languageCode?.let { detectedLocales.add(it) }
            }
        }

        val outputFile =
            outputDir.file("com/machiav3lli/derdiedas/locales/DetectedLocales.kt").get().asFile
        outputFile.parentFile.mkdirs()
        outputFile.writeText(
            """
            package com.machiav3lli.derdiedas.locales
            
            object DetectedLocales {
                val ALL: Array<String> = arrayOf(${
                detectedLocales.sorted().joinToString { "\"$it\"" }
            })
            }
        """.trimIndent()
        )

        println("Generated locales to ${outputFile.absolutePath}: ${detectedLocales.size} languages")
    }
}