package com.app.composetraining.framework.datasource.cache.database

import androidx.room.*
import com.app.composetraining.framework.datasource.cache.model.PhotoCacheEntity

@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(photoCacheEntity: PhotoCacheEntity)

    @Delete
    suspend fun delete(photoCacheEntity: PhotoCacheEntity)

    @Query("DELETE FROM photos_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM photos_table")
    suspend fun get(): List<PhotoCacheEntity>

    @Query("SELECT * FROM photos_table WHERE primary_key_id LIKE :id")
    suspend fun getPhotoById(id: Int): PhotoCacheEntity
}
