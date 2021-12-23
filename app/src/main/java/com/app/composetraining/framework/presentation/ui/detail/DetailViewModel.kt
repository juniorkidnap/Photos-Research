package com.app.composetraining.framework.presentation.ui.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.composetraining.buisiness.domain.models.Photo
import com.app.composetraining.buisiness.interactors.PhotosInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for [DetailScreen]
 */
@HiltViewModel
class DetailViewModel
@Inject
constructor(
    private val interactor: PhotosInteractor,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    /**
     * Currently displaying photo
     */
    val currentPhoto = mutableStateOf(Photo())

    /**
     * Searching for [Photo] in database by [primaryKeyId] of [Photo]
     * and setting up in [_currentPhoto] MutableState,
     * which used by [DetailScreen]
     */
    init {
        savedStateHandle.get<Int>("photoId")?.also { photoId ->
            if (photoId != -1) {
                viewModelScope.launch {
                    currentPhoto.value = interactor.getPhotoById(photoId)
                }
            }
        }
    }
}
