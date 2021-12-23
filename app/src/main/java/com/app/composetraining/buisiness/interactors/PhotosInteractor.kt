package com.app.composetraining.buisiness.interactors

import android.util.Log
import com.app.composetraining.buisiness.data.cache.CacheDataSource
import com.app.composetraining.buisiness.data.network.NetworkDataSource
import com.app.composetraining.buisiness.domain.models.Photo
import com.app.composetraining.buisiness.domain.state.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * This class Interacts with [NetworkDataSource] and [CacheDataSource]
 */
class PhotosInteractor
@Inject
constructor(
    private val networkDataSource: NetworkDataSource,
    private val cacheDataSource: CacheDataSource
) {

    companion object {
        const val API_KEY = "bfa3bbd5770e2b0ba01bdb2696ef2c11"
        const val API_METHOD = "flickr.photos.search"
        const val API_FORMAT = "json"
        const val API_NO_JSON_CALLBACK = "true"
        const val API_EXTRA_ONE = "media"
        const val API_EXTRA_TWO = "url_sq"
        const val API_EXTRA_THREE = "url_m"
    }

    /**
     * Getting data from Network, passing it to local Cache Storage
     *
     * @param searchQuery - users input
     * @param perPage - amount of photos getting from one page
     * @param page - number of page
     *
     * @return [DataState] result by [Flow]
     */
    suspend fun execute(
        searchQuery: String,
        perPage: Int,
        page: Int
    ): Flow<DataState<List<Photo>>> = flow {
        try {
            emit(DataState.Loading)
            val response = networkDataSource.getPhotos(
                API_KEY,
                API_METHOD,
                searchQuery,
                API_FORMAT,
                API_NO_JSON_CALLBACK,
                API_EXTRA_ONE,
                API_EXTRA_TWO,
                API_EXTRA_THREE,
                perPage = perPage.toString(),
                page = page.toString()
            )
            cacheDataSource.insertList(response)
            val listPhotos = cacheDataSource.get()
            emit(DataState.Success(listPhotos))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error)
        }
    }

    /**
     * Clearing data cache
     */
    suspend fun clearCache() {
        try {
            cacheDataSource.deleteAll()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Getting photo from Local Cache storage
     *
     * @return [Photo] with [photoId] if it was found
     */
    suspend fun getPhotoById(photoId: Int): Photo {
        try {
            return cacheDataSource.getPhotoById(id = photoId)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Photo()
    }
}
