package com.example.productsStore.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("app_config")

@Singleton
class ConfigDataStore @Inject constructor(
    @ApplicationContext private val appContext: Context
) {

    val isOnlineFlow: Flow<Boolean> = appContext.dataStore.data
        .map { preferences -> preferences[isOnlineKey] ?: true }

    suspend fun shouldDisplayCartHint() =
        appContext.dataStore.data.first()[shouldDisplayCartHintKey] ?: true
    suspend fun updateShouldDisplayCartHint() {
        appContext.dataStore.edit { preferences ->
            preferences[shouldDisplayCartHintKey] = false
        }
    }
    suspend fun updateOnlineStatus(isOnline: Boolean) {
        appContext.dataStore.edit { preferences ->
            preferences[isOnlineKey] = isOnline
        }
    }

    private val isOnlineKey = booleanPreferencesKey("is_online")
    private val shouldDisplayCartHintKey = booleanPreferencesKey("should_display_cart_hint")
}