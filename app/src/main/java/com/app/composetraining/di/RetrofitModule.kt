package com.app.composetraining.di

import com.app.composetraining.buisiness.data.network.NetworkDataSource
import com.app.composetraining.buisiness.data.network.NetworkDataSourceImpl
import com.app.composetraining.framework.datasource.network.api.mapper.ModelNetworkMapper
import com.app.composetraining.framework.datasource.network.api.retrofit.ModelRetrofit
import com.app.composetraining.framework.datasource.network.api.service.ModelRetrofitService
import com.app.composetraining.framework.datasource.network.api.service.ModelRetrofitServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Providing dependencies for [Retrofit]
 */
@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private const val API_URL = "https://api.flickr.com/"

    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun providesApiRetrofit(okHttpClient: OkHttpClient): ModelRetrofit {
        return Retrofit.Builder()
            .baseUrl(API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ModelRetrofit::class.java)
    }

    @Singleton
    @Provides
    fun providesSearchRetrofitService(
        modelRetrofit: ModelRetrofit
    ): ModelRetrofitService {
        return ModelRetrofitServiceImpl(modelRetrofit)
    }

    @Singleton
    @Provides
    fun providesNetworkDataSource(
        modelRetrofitService: ModelRetrofitService,
        modelNetworkMapper: ModelNetworkMapper
    ): NetworkDataSource {
        return NetworkDataSourceImpl(
            modelRetrofitService,
            modelNetworkMapper
        )
    }
}
