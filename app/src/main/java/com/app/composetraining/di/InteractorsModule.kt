package com.app.composetraining.di

import android.content.Context
import com.app.composetraining.buisiness.data.cache.CacheDataSource
import com.app.composetraining.buisiness.data.network.NetworkDataSource
import com.app.composetraining.buisiness.interactors.PhotosInteractor
import com.app.composetraining.buisiness.interactors.MediaSaverInteractor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Providing dependencies for [Interactors]
 */
@Module
@InstallIn(SingletonComponent::class)
object InteractorsModule {

    @Singleton
    @Provides
    fun providesGetPhotos(
        networkDataSource: NetworkDataSource,
        cacheDataSource: CacheDataSource
    ): PhotosInteractor {
        return PhotosInteractor(
            networkDataSource,
            cacheDataSource
        )
    }

    @Singleton
    @Provides
    fun providesMediaSaver(
        @ApplicationContext appContext: Context
    ): MediaSaverInteractor {
        return MediaSaverInteractor(appContext)
    }
}
