package com.app.composetraining.framework.datasource.network.api.service

import com.app.composetraining.framework.datasource.network.api.model.ResponseNetworkEntity

interface ModelRetrofitService {

    suspend fun get(
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
    ): ResponseNetworkEntity
}
