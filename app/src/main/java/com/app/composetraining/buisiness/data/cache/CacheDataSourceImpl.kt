package com.app.composetraining.buisiness.data.cache

import android.util.Log
import com.app.composetraining.buisiness.domain.models.Photo
import com.app.composetraining.framework.datasource.cache.mapper.PhotoCacheMapper
import com.app.composetraining.framework.datasource.cache.service.PhotoDaoService

class CacheDataSourceImpl
constructor(
    private val photoDaoService: PhotoDaoService,
    private val photoCacheMapper: PhotoCacheMapper
) : CacheDataSource {

    override suspend fun insert(photo: Photo) {
        photoDaoService.insert(photoCacheMapper.mapToEntity(photo))
    }

    override suspend fun delete(photo: Photo) {
        photoDaoService.delete(photoCacheMapper.mapToEntity(photo))
    }

    override suspend fun deleteAll() {
        photoDaoService.deleteAll()
    }

    override suspend fun insertList(photoList: List<Photo>) {
        for (photo in photoList) {
            insert(photo)
        }
    }

    override suspend fun get(): List<Photo> {
        return photoCacheMapper.mapFromEntityList(
            photoDaoService.get()
        )
    }

    override suspend fun getPhotoById(id: Int): Photo {
        return photoCacheMapper.mapFromEntity(photoDaoService.getPhotoById(id))
    }
}
