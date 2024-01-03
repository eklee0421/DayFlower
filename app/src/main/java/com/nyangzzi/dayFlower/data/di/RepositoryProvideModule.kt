package com.nyangzzi.dayFlower.data.di

import android.content.Context
import com.nyangzzi.dayFlower.data.repository.LoginRepositoryImpl
import com.nyangzzi.dayFlower.data.repository.SearchFlowerRepositoryImpl
import com.nyangzzi.dayFlower.domain.repository.LoginRepository
import com.nyangzzi.dayFlower.domain.repository.SearchFlowerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    fun bindLoginRepository(
        @ApplicationContext context: Context
    ): LoginRepository {
        return LoginRepositoryImpl(context)
    }

}