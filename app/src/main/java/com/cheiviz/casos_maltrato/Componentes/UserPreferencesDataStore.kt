package com.cheiviz.casos_maltrato.Componentes


import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPreferencesDataStore(private val context: Context) {
    companion object {
        val NICKNAME = stringPreferencesKey("nickname")
        val ANONIMO = booleanPreferencesKey("anonimo")
    }

    val userName: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[NICKNAME] ?: "Anónimo"
    }

    val isAnonimo: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[ANONIMO] ?: false
    }

    suspend fun saveUserData(name: String, anonimo: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[NICKNAME] = name
            prefs[ANONIMO] = anonimo
        }
    }
}
