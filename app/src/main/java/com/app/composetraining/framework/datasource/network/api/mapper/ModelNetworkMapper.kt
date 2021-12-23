package com.app.composetraining.framework.datasource.network.api.mapper

import com.app.composetraining.buisiness.domain.models.Photo
import com.app.composetraining.buisiness.domain.utilities.EntityMapper
import com.app.composetraining.framework.datasource.network.api.model.PhotoNetworkEntity
import javax.inject.Inject

class ModelNetworkMapper
@Inject constructor() : EntityMapper<PhotoNetworkEntity, Photo> {

    override fun mapFromEntity(entity: PhotoNetworkEntity): Photo {
        return Photo(
            farm = entity.farm,
            height = entity.height,
            networkId = entity.id,
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

    override fun mapToEntity(domainModel: Photo): PhotoNetworkEntity {
        return PhotoNetworkEntity(
            farm = domainModel.farm,
            height = domainModel.height,
            id = domainModel.networkId,
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

    fun mapFromEntityList(entities: List<PhotoNetworkEntity>): List<Photo> {
        return entities.map { mapFromEntity(it) }
    }
}
