package com.machiav3lli.derdiedas.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

object PrefKey {
    val THEME = stringPreferencesKey("themes")
    val LANGUAGE = stringPreferencesKey("language")
    val FIRST_RUN = booleanPreferencesKey("firstrun")
}

abstract class PrefDelegate<T>(
    protected val dataStore: DataStore<Preferences>,
    protected val defaultValue: T
) {
    abstract val flow: Flow<T>
    abstract suspend fun set(value: T)

    val value: T
        get() = runBlocking { flow.first() }
}

class PrefString<E : Enum<E>>(
    dataStore: DataStore<Preferences>,
    private val key: Preferences.Key<String>,
    defaultValue: String,
    private val entries: Set<E>? = null
) : PrefDelegate<String>(dataStore, defaultValue) {
    override val flow: Flow<String> = dataStore.data.map { preferences ->
        preferences[key] ?: defaultValue
    }

    override suspend fun set(value: String) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }
}

class PrefBoolean(
    dataStore: DataStore<Preferences>,
    private val key: Preferences.Key<Boolean>,
    defaultValue: Boolean
) : PrefDelegate<Boolean>(dataStore, defaultValue) {
    override val flow: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[key] ?: defaultValue
    }

    override suspend fun set(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }
}