package com.app.composetraining.framework.datasource.cache.service

import com.app.composetraining.framework.datasource.cache.model.PhotoCacheEntity

interface PhotoDaoService {

    suspend fun insert(photoCacheEntity: PhotoCacheEntity)
    suspend fun delete(photoCacheEntity: PhotoCacheEntity)
    suspend fun deleteAll()
    suspend fun get(): List<PhotoCacheEntity>
    suspend fun getPhotoById(id: Int): PhotoCacheEntity
}
