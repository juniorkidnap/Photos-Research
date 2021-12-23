package com.app.composetraining.framework.datasource.cache.service

import com.app.composetraining.framework.datasource.cache.database.PhotoDao
import com.app.composetraining.framework.datasource.cache.model.PhotoCacheEntity

class PhotoDaoServiceImpl
constructor(
    private val photoDao: PhotoDao
) : PhotoDaoService {

    override suspend fun insert(photoCacheEntity: PhotoCacheEntity) {
        return photoDao.insert(photoCacheEntity)
    }

    override suspend fun delete(photoCacheEntity: PhotoCacheEntity) {
        return photoDao.delete(photoCacheEntity)
    }

    override suspend fun deleteAll() {
        return photoDao.deleteAll()
    }

    override suspend fun get(): List<PhotoCacheEntity> {
        return photoDao.get()
    }

    override suspend fun getPhotoById(id: Int): PhotoCacheEntity {
        return photoDao.getPhotoById(id)
    }
}
