package com.app.composetraining.framework.datasource.network.api.service

import com.app.composetraining.framework.datasource.network.api.model.ResponseNetworkEntity
import com.app.composetraining.framework.datasource.network.api.retrofit.ModelRetrofit

class ModelRetrofitServiceImpl
constructor(
    private val modelRetrofit: ModelRetrofit
) : ModelRetrofitService {

    override suspend fun get(
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
    ): ResponseNetworkEntity {
        return modelRetrofit.get(
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
        )
    }
}
