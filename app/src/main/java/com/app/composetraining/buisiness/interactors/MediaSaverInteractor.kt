package com.app.composetraining.buisiness.interactors

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import com.app.composetraining.R
import com.app.composetraining.framework.presentation.ui.MainActivity
import com.app.composetraining.framework.presentation.utils.COMPRESS_QUALITY
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.util.*
import javax.inject.Inject

/**
 * Saving photo to external storage
 */
class MediaSaverInteractor
@Inject
constructor(
    @ApplicationContext val appContext: Context
) {

    /**
     * Downloading photo with [Glide] from [url]
     *
     * @param url is photo url
     */
    fun downloadPhoto(
        url: String?
    ) {
        Glide.with(appContext.applicationContext)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap?>() {

                @RequiresApi(Build.VERSION_CODES.M)
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap?>?
                ) {
                    savePhotoToExternalStorage(UUID.randomUUID().toString(), resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }

    /**
     * When resource ready, checking SDK version to choose correct way to save photo
     * @return result of saving - true if saved successfully, false if saving failed
     */
    private fun savePhotoToExternalStorage(name: String, bitmap: Bitmap?): Boolean {
        val imageCollection: Uri = if (sdkCheck()) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }
        val contentValues = ContentValues().apply {
            put(
                MediaStore.Images.Media.DISPLAY_NAME,
                "$name${appContext.getString(R.string.text_image_extension)}"
            )
            put(MediaStore.Images.Media.MIME_TYPE, appContext.getString(R.string.text_mime_type))
            if (bitmap != null) {
                put(MediaStore.Images.Media.WIDTH, bitmap.width)
                put(MediaStore.Images.Media.HEIGHT, bitmap.height)
            }
        }
        return try {
            appContext.applicationContext.contentResolver.insert(imageCollection, contentValues)
                ?.also {
                    appContext.applicationContext.contentResolver.openOutputStream(it)
                        .use { outputStream ->
                            if (bitmap != null) {
                                if (!bitmap.compress(
                                        Bitmap.CompressFormat.JPEG,
                                        COMPRESS_QUALITY,
                                        outputStream
                                    )
                                ) {
                                    throw IOException(appContext.getString(R.string.error_bitmap))
                                }
                            }
                        }
                } ?: throw IOException(appContext.getString(R.string.error_media_store))
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    /**
     * Checking SDK version of Device
     */
    private fun sdkCheck(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return true
        }
        return false
    }

    /**
     * Checking for permission access to external storage
     */
    suspend fun checkPermission(): Flow<Boolean> = flow {
        if (MainActivity.isWritePermissionGranted && MainActivity.isReadPermissionGranted) {
            emit(true)
        } else {
            emit(false)
        }
    }
}
