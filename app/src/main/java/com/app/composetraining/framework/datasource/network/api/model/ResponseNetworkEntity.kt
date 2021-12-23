package com.app.composetraining.framework.datasource.network.api.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponseNetworkEntity(

    @SerializedName("photos") @Expose
    val photos: PhotosListNetworkEntity,

    @SerializedName("stat") @Expose
    val stat: String? = null
)

data class PhotosListNetworkEntity(

    @SerializedName("page") @Expose
    val page: Int? = null,

    @SerializedName("pages") @Expose
    val pages: Int? = null,

    @SerializedName("perpage") @Expose
    val perPage: Int? = null,

    @SerializedName("photo") @Expose
    val listPhoto: List<PhotoNetworkEntity>,

    @SerializedName("total") @Expose
    val total: Int? = null
)

data class PhotoNetworkEntity(

    @SerializedName("farm") @Expose
    val farm: Int? = null,

    @SerializedName("height_m") @Expose
    val height: Int? = null,

    @SerializedName("id") @Expose
    val id: String? = null,

    @SerializedName("isfamily") @Expose
    val isFamily: Int? = null,

    @SerializedName("isfriend") @Expose
    val isFriend: Int? = null,

    @SerializedName("ispublic") @Expose
    val isPublic: Int? = null,

    @SerializedName("owner") @Expose
    val ownerId: String? = null,

    @SerializedName("secret") @Expose
    val secretCode: String? = null,

    @SerializedName("server") @Expose
    val serverId: String? = null,

    @SerializedName("title") @Expose
    val title: String? = null,

    @SerializedName("url_m") @Expose
    val url: String? = null,

    @SerializedName("width_m") @Expose
    val width: Int? = null
)
