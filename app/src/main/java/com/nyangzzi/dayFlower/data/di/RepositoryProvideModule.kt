package com.nyangzzi.dayFlower.data.di

import android.content.Context
import com.nyangzzi.dayFlower.data.repository.GetFirebaseRepositoryImpl
import com.nyangzzi.dayFlower.data.repository.LoginRepositoryImpl
import com.nyangzzi.dayFlower.data.repository.OpenSourceLicenseRepositoryImpl
import com.nyangzzi.dayFlower.data.repository.SearchFlowerRepositoryImpl
import com.nyangzzi.dayFlower.domain.repository.GetFirebaseRepository
import com.nyangzzi.dayFlower.domain.repository.LoginRepository
import com.nyangzzi.dayFlower.domain.repository.OpenSourceLicenseRepository
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


    @Singleton
    @Provides
    fun bindLoginRepository(
        @ApplicationContext context: Context
    ): LoginRepository {
        return LoginRepositoryImpl(context)
    }

    @Singleton
    @Provides
    fun bindGetFirebaseRepository(
    ): GetFirebaseRepository {
        return GetFirebaseRepositoryImpl()
    }

    @Singleton
    @Provides
    fun bindOpenSourceLicense(
        @ApplicationContext context: Context
    ): OpenSourceLicenseRepository {
        return OpenSourceLicenseRepositoryImpl(context)
    }

}