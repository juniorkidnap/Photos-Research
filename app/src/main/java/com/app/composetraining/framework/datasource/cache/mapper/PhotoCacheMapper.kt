package com.app.composetraining.framework.datasource.cache.mapper

import com.app.composetraining.buisiness.domain.models.Photo
import com.app.composetraining.buisiness.domain.utilities.EntityMapper
import com.app.composetraining.framework.datasource.cache.model.PhotoCacheEntity
import javax.inject.Inject

class PhotoCacheMapper
@Inject
constructor() : EntityMapper<PhotoCacheEntity, Photo> {

    override fun mapFromEntity(entity: PhotoCacheEntity): Photo {
        return Photo(
            primaryKeyId = entity.primaryKeyId,
            farm = entity.farm,
            height = entity.height,
            networkId = entity.networkId,
            isFamily = entity.isFamily,
            isFriend = entity.isFriend,
            isPublic = entity.isPublic,
            ownerId = entity.ownerId,
            secretCode = entity.secretCode,
            serverId = entity.serverId,
            title = entity.title,
            url = entity.url,
            width = entity.width
        )
    }

    override fun mapToEntity(domainModel: Photo): PhotoCacheEntity {
        return PhotoCacheEntity(
            primaryKeyId = domainModel.primaryKeyId,
            farm = domainModel.farm,
            height = domainModel.height,
            networkId = domainModel.networkId,
            isFamily = domainModel.isFamily,
            isFriend = domainModel.isFriend,
            isPublic = domainModel.isPublic,
            ownerId = domainModel.ownerId,
            secretCode = domainModel.secretCode,
            serverId = domainModel.serverId,
            title = domainModel.title,
            url = domainModel.url,
            width = domainModel.width
        )
    }

    fun mapFromEntityList(entities: List<PhotoCacheEntity>): List<Photo> {
        return entities.map { mapFromEntity(it) }
    }
}
