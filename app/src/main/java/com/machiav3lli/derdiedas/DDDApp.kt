package com.machiav3lli.derdiedas

import android.app.Application
import com.machiav3lli.derdiedas.data.databaseModule
import com.machiav3lli.derdiedas.ui.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androix.startup.KoinStartup
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.koinConfiguration

class DDDApp : Application(), KoinStartup {
    @KoinExperimentalAPI
    override fun onKoinStartup() = koinConfiguration {
        androidLogger()
        androidContext(this@DDDApp)
        modules(
            databaseModule,
            viewModelsModule,
        )
    }
}