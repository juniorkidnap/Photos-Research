package com.app.composetraining.buisiness.domain.models

/**
 * domain model, contains info about downloaded photo
 */
data class Photo(

    val primaryKeyId: Int? = null,
    val farm: Int? = null,
    val height: Int? = null,
    val networkId: String? = null,
    val isFamily: Int? = null,
    val isFriend: Int? = null,
    val isPublic: Int? = null,
    val ownerId: String? = null,
    val secretCode: String? = null,
    val serverId: String? = null,
    val title: String? = null,
    val url: String? = null,
    val width: Int? = null,

    var selected: Boolean = false
)
