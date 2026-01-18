package com.machiav3lli.derdiedas.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.machiav3lli.derdiedas.ui.MainScreen
import com.machiav3lli.derdiedas.ui.SettingsScreen
import com.machiav3lli.derdiedas.ui.StatsScreen
import com.machiav3lli.derdiedas.ui.WordScreen

@Composable
fun AppNavDisplay(
    backStack: NavBackStack<NavRoute>,
    modifier: Modifier = Modifier,
) {
    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        transitionSpec = {
            slideInHorizontally(tween(600)) { it } togetherWith
                    slideOutHorizontally(tween(600)) { -it }
        },
        popTransitionSpec = {
            slideInHorizontally(tween(600)) { -it } togetherWith
                    slideOutHorizontally(tween(600)) { it }
        },
        predictivePopTransitionSpec = {
            slideInHorizontally(tween(600)) { -it } togetherWith
                    slideOutHorizontally(tween(600)) { it }
        },
        entryProvider = entryProvider {
            slideInEntry<NavRoute.Main> { key ->
                MainScreen()
            }
            slideInEntry<NavRoute.Word> { key ->
                WordScreen {
                    backStack.removeLastOrNull()
                }
            }
            slideInEntry<NavRoute.Settings> { key ->
                SettingsScreen {
                    backStack.removeLastOrNull()
                }
            }
            slideInEntry<NavRoute.Stats> { key ->
                StatsScreen {
                    backStack.removeLastOrNull()
                }
            }
        }
    )
}

inline fun <reified K : NavRoute> EntryProviderScope<NavRoute>.slideInEntry(
    metadata: Map<String, Any> = emptyMap(),
    noinline content: @Composable (K) -> Unit,
) {
    entry<K>(
        metadata = metadata + NavDisplay.transitionSpec {
            slideInHorizontally(tween(600)) { it } togetherWith
                    slideOutHorizontally(tween(600)) { -it }
        } + NavDisplay.popTransitionSpec {
            slideInHorizontally(tween(600)) { -it } togetherWith
                    slideOutHorizontally(tween(600)) { it }
        } + NavDisplay.predictivePopTransitionSpec {
            slideInHorizontally(tween(600)) { -it } togetherWith
                    slideOutHorizontally(tween(600)) { it }
        }
    ) {
        content(it)
    }
}