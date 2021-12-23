package com.app.composetraining.framework.datasource.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos_table")
data class PhotoCacheEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "primary_key_id")
    val primaryKeyId: Int? = null,

    @ColumnInfo(name = "id")
    val networkId: String? = null,

    @ColumnInfo(name = "url")
    val url: String? = null,

    @ColumnInfo(name = "title")
    val title: String? = null,

    @ColumnInfo(name = "farm")
    val farm: Int? = null,

    @ColumnInfo(name = "height")
    val height: Int? = null,

    @ColumnInfo(name = "is_family")
    val isFamily: Int? = null,

    @ColumnInfo(name = "is_friend")
    val isFriend: Int? = null,

    @ColumnInfo(name = "is_public")
    val isPublic: Int? = null,

    @ColumnInfo(name = "owner_id")
    val ownerId: String? = null,

    @ColumnInfo(name = "secret_code")
    val secretCode: String? = null,

    @ColumnInfo(name = "server_id")
    val serverId: String? = null,

    @ColumnInfo(name = "width")
    val width: Int? = null
)
