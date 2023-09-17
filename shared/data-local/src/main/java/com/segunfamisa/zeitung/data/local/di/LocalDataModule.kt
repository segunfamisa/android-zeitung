package com.segunfamisa.zeitung.data.local.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.segunfamisa.zeitung.data.local.preferences.Prefs
import com.segunfamisa.zeitung.data.local.preferences.UserPreferencesSerializer
import com.segunfamisa.zeitung.data.local.preferences.UserPreferencesSourceImpl
import com.segunfamisa.zeitung.data.preferences.UserPreferencesSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class LocalDataModule {

    companion object {

        @Provides
        @JvmStatic
        @Singleton
        internal fun providesDataStore(context: Context): DataStore<Prefs> {
            return DataStoreFactory.create(
                produceFile = { context.dataStoreFile("user-prefs.pb") },
                corruptionHandler = null,
                serializer = UserPreferencesSerializer,
                migrations = listOf(),
            )
        }
    }

    @Binds
    internal abstract fun bindUserPreferencesSource(impl: UserPreferencesSourceImpl): UserPreferencesSource
}
