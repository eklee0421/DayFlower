package com.nyangzzi.dayFlower.data.base

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "a")

class DataStoreSource @Inject constructor(context: Context) {

    private val applicationContext = context.applicationContext

    fun getValue(key: String): Flow<String?> =
        applicationContext.dataStore.data.map {
            it[stringPreferencesKey(key)]
        }

    suspend fun setValue(key: String, value: String) {
        applicationContext.dataStore.edit {
            it[stringPreferencesKey(key)] = value
        }
    }

}