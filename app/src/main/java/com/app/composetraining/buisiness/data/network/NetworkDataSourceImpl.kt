package com.app.composetraining.buisiness.data.network

import com.app.composetraining.buisiness.domain.models.Photo
import com.app.composetraining.framework.datasource.network.api.mapper.ModelNetworkMapper
import com.app.composetraining.framework.datasource.network.api.service.ModelRetrofitService

class NetworkDataSourceImpl
constructor(
    private val modelRetrofitService: ModelRetrofitService,
    private val modelNetworkMapper: ModelNetworkMapper
) : NetworkDataSource {

    override suspend fun getPhotos(
        apiKey: String,
        from: String,
        to: String,
        format: String,
        noJsonCallback: String,
        extrasOne: String,
        extrasTwo: String,
        extrasThree: String,
        perPage: String,
        page: String
    ): List<Photo> {
        return modelNetworkMapper
            .mapFromEntityList(
                modelRetrofitService.get(
                    apiKey,
                    from,
                    to,
                    format,
                    noJsonCallback,
                    extrasOne,
                    extrasTwo,
                    extrasThree,
                    perPage,
                    page
                ).photos.listPhoto
            )
    }
}
