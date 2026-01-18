package com.machiav3lli.derdiedas.ui

import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.machiav3lli.derdiedas.data.AppPrefs
import com.machiav3lli.derdiedas.data.NounDao
import com.machiav3lli.derdiedas.data.Themes
import com.machiav3lli.derdiedas.data.WordViewModel
import com.machiav3lli.derdiedas.ui.theme.AppTheme
import com.machiav3lli.derdiedas.ui.theme.isNightMode
import com.machiav3lli.derdiedas.utils.createNounListFromAsset
import kotlinx.coroutines.runBlocking
import org.koin.compose.koinInject
import org.koin.core.component.get
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val prefs: AppPrefs = koinInject()
            val currentTheme by prefs.appTheme.flow.collectAsState(initial = Themes.SYSTEM.value)

            DisposableEffect(currentTheme) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        android.graphics.Color.TRANSPARENT,
                        android.graphics.Color.TRANSPARENT,
                    ) { isNightMode() },
                    navigationBarStyle = SystemBarStyle.auto(
                        android.graphics.Color.TRANSPARENT,
                        android.graphics.Color.TRANSPARENT,
                    ) { isNightMode() },
                )
                onDispose {}
            }

            DisposableEffect(prefs) {
                if (prefs.firstRun.value) {
                    runBlocking {
                        this@MainActivity.get<NounDao>().let {
                            it.deleteAll()
                            it.insertAll(createNounListFromAsset())
                            prefs.firstRun.set(false)
                        }
                    }
                }
                onDispose { }
            }

            AppTheme {
                Scaffold(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                    contentColor = MaterialTheme.colorScheme.onBackground,
                ) {
                    MainScreen(Modifier.padding(it))
                }
            }
        }
    }
}

val viewModelsModule = module {
    singleOf(::WordViewModel)
}