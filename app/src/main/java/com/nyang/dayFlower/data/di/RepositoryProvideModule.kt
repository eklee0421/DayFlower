package com.nyang.dayFlower.data.di

import com.nyang.dayFlower.data.repository.SearchFlowerRepositoryImpl
import com.nyang.dayFlower.domain.repository.SearchFlowerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryProvideModule {
    @Singleton
    @Provides
    fun bindSearchFlowerRepository(
    ): SearchFlowerRepository {
        return SearchFlowerRepositoryImpl()
    }

}