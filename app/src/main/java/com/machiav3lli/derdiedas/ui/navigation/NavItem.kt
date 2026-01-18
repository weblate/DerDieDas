package com.machiav3lli.derdiedas.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable


@Serializable
sealed class NavRoute : NavKey {
    @Serializable
    data object Main : NavRoute()

    @Serializable
    data object Word : NavRoute()

    @Serializable
    data object Settings : NavRoute()

    @Serializable
    data object Stats : NavRoute()
}