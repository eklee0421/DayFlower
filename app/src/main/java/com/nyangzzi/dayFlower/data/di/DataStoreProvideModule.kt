package com.nyangzzi.dayFlower.data.di

import android.content.Context
import com.nyangzzi.dayFlower.data.base.DataStoreSource
import com.nyangzzi.dayFlower.data.repository.DataStoreRepositoryImpl
import com.nyangzzi.dayFlower.domain.repository.DataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreProvideModule {
    @Singleton
    @Provides
    fun bindDataStoreSource(
        @ApplicationContext context: Context
    ): DataStoreSource {
        return DataStoreSource(context)
    }

    @Singleton
    @Provides
    fun bindDataStoreRepository(
        dataStoreSource: DataStoreSource
    ): DataStoreRepository {
        return DataStoreRepositoryImpl(dataStoreSource)
    }

}