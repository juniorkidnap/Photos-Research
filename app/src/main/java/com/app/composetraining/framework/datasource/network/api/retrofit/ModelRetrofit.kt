package com.app.composetraining.framework.datasource.network.api.retrofit

import com.app.composetraining.framework.datasource.network.api.model.ResponseNetworkEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface ModelRetrofit {

    @GET("./services/rest")
    suspend fun get(
        @Query("api_key") apiKey: String,
        @Query("method") from: String,
        @Query("tags") to: String,
        @Query("format") format: String,
        @Query("nojsoncallback") noJsonCallback: String,
        @Query("extras") extrasOne: String,
        @Query("extras") extrasTwo: String,
        @Query("extras") extrasThree: String,
        @Query("per_page") perPage: String,
        @Query("page") page: String,
    ): ResponseNetworkEntity
}
