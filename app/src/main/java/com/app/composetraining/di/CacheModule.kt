package com.app.composetraining.di

import android.content.Context
import androidx.room.Room
import com.app.composetraining.buisiness.data.cache.CacheDataSource
import com.app.composetraining.buisiness.data.cache.CacheDataSourceImpl
import com.app.composetraining.framework.datasource.cache.database.MyDatabase
import com.app.composetraining.framework.datasource.cache.database.PhotoDao
import com.app.composetraining.framework.datasource.cache.mapper.PhotoCacheMapper
import com.app.composetraining.framework.datasource.cache.service.PhotoDaoService
import com.app.composetraining.framework.datasource.cache.service.PhotoDaoServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Providing dependencies for [Room]
 */
@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun providesMyDatabase(
        @ApplicationContext appContext: Context
    ): MyDatabase {
        return Room.databaseBuilder(
            appContext,
            MyDatabase::class.java,
            MyDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providesPhotoDao(
        myDatabase: MyDatabase
    ): PhotoDao {
        return myDatabase.PhotoDao()
    }

    @Singleton
    @Provides
    fun providePhotoDaoService(
        photoDao: PhotoDao
    ): PhotoDaoService {
        return PhotoDaoServiceImpl(photoDao)
    }

    @Singleton
    @Provides
    fun providesCacheDataSource(
        photoDaoService: PhotoDaoService,
        photoCacheMapper: PhotoCacheMapper
    ): CacheDataSource {
        return CacheDataSourceImpl(
            photoDaoService,
            photoCacheMapper
        )
    }
}
