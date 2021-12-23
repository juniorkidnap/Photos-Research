package com.app.composetraining.buisiness.data.network

import com.app.composetraining.buisiness.domain.models.Photo

/**
 * Interface that contains functions to manage work between network datasource and
 * business rules
 */
interface NetworkDataSource {

    suspend fun getPhotos(
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
    ): List<Photo>
}
