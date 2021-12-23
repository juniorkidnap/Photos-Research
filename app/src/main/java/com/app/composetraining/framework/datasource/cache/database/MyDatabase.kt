package com.app.composetraining.framework.datasource.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.composetraining.framework.datasource.cache.model.PhotoCacheEntity

@Database(entities = [PhotoCacheEntity::class], version = 1)
abstract class MyDatabase : RoomDatabase() {

    abstract fun PhotoDao(): PhotoDao

    companion object {
        const val DATABASE_NAME: String = "photos_db"
    }
}
