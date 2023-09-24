package com.nyang.dayFlower.data.di

import com.nyang.dayFlower.data.repository.SearchFlowerRepositoryImpl
import com.nyang.dayFlower.domain.repository.SearchFlowerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryProvideModule {
    @Provides
    fun bindSearchFlowerRepository(
    ): SearchFlowerRepository {
        return SearchFlowerRepositoryImpl()
    }

}