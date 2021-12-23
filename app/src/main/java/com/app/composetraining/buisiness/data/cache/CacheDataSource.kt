package com.app.composetraining.buisiness.data.cache

import com.app.composetraining.buisiness.domain.models.Photo

/**
 * Interface that contains functions to manage work between cache datasource and
 * business rules
 */
interface CacheDataSource {

    suspend fun insert(photo: Photo)
    suspend fun delete(photo: Photo)
    suspend fun deleteAll()
    suspend fun insertList(photoList: List<Photo>)
    suspend fun get(): List<Photo>
    suspend fun getPhotoById(id: Int): Photo
}
