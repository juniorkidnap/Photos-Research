package com.app.composetraining.framework.presentation.ui.search

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.composetraining.buisiness.domain.models.Photo
import com.app.composetraining.buisiness.domain.state.DataState
import com.app.composetraining.buisiness.interactors.MediaSaverInteractor
import com.app.composetraining.buisiness.interactors.PhotosInteractor
import com.app.composetraining.framework.presentation.utils.PHOTOS_PER_PAGE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
@Inject
constructor(
    private val photosInteractor: PhotosInteractor,
    private val mediaSaver: MediaSaverInteractor
) : ViewModel() {

    companion object {
        const val ONE_MORE_PHOTO = 1
        const val SNACKBAR_TEXT = "Saving photos"
        const val PERMISSION_DENIED = "Permission denied"
    }

    var isLoading = mutableStateOf(false)
    var isError = mutableStateOf(false)
    var listPhotos = mutableStateListOf<Photo>()
    var selectedPhotos = mutableStateListOf<Photo>()
    var selectedPhotosCounter = mutableStateOf(0)
    var searchQuery = mutableStateOf("")
    val page = mutableStateOf(1)

    private val _screenEventFlow = MutableSharedFlow<SearchScreenEvents>()
    val screenEventFlow get() = _screenEventFlow.asSharedFlow()

    private var scrollPosition = 0

    /**
     * Registers events and process them
     */
    fun onEvent(event: SearchEvent) {
        viewModelScope.launch {
            when (event) {
                is SearchEvent.NewSearchEvent -> {
                    photosInteractor.clearCache()
                    newSearch(event.searchQuery)
                }
                is SearchEvent.NextPageEvent -> {
                    nextPage()
                }
            }
        }
    }

    /**
     * Start new search on [NewSearchEvent] and making request
     *
     * @param searchQuery (state) is user input
     */
    private fun newSearch(searchQuery: String) {
        listPhotos.clear()
        page.value = 1
        makeRequest(
            searchQuery = searchQuery,
            perPage = PHOTOS_PER_PAGE,
            page = page.value
        )
        this.searchQuery.value = searchQuery
    }

    /**
     * Making a new request for second page to get 21st photo
     */
    private fun nextPage() {
        if (page.value == 1) {
            page.value++
            makeRequest(
                searchQuery = searchQuery.value,
                perPage = ONE_MORE_PHOTO,
                page = page.value
            )
        }
    }

    /**
     * Setting list scroll position to check if it's time to get
     * 2nd page
     *
     * @param position (state) ListScroll position
     */
    fun setListScrollPosition(position: Int) {
        scrollPosition = position
    }

    /**
     * Making new request to get data from Flick API.
     * Setting [DataState] with [MutableStateFlow].
     * if success -> [DataState.Success], loading data to [listPhotos]
     * if loading -> [DataState.Loading], [isLoading] = true
     * if error -> [DataState.Error], [isError] = true
     *
     * @param searchQuery (state) user query
     * @param perPage (state) amount items to get from request
     * @param page (state) number of page
     */
    private fun makeRequest(
        searchQuery: String,
        perPage: Int,
        page: Int
    ) {
        this.searchQuery.value = searchQuery
        viewModelScope.launch {
            photosInteractor.execute(
                searchQuery = searchQuery,
                perPage = perPage,
                page = page
            ).collect { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        submitData(dataState.data)
                        isLoading.value = false
                        isError.value = false
                    }
                    DataState.Loading -> {
                        isLoading.value = true
                        isError.value = false
                    }
                    DataState.Error -> {
                        isError.value = true
                        isLoading.value = false
                    }
                }
            }
        }
    }

    /**
     * Adding photos to [listPhotos]
     * if its new request add all data from response to [listPhotos]
     * if [listPhotos] is not empty -> adding new items
     *
     * @param photos (state) list of photos
     */
    private fun submitData(photos: List<Photo>) {
        if (listPhotos.isEmpty()) {
            listPhotos.addAll(photos)
        } else {
            for (photo in photos) {
                if (!listPhotos.contains(photo)) {
                    listPhotos.add(photo)
                }
            }
        }
    }

    /**
     * Checking is [Photo] selected
     * if true -> unselecting and remove from [selectedPhotos] list
     * if false -> selecting and add to [selectedPhotos] list
     *
     * @param photo - photo selected by user
     */
    fun checkIsSelected(photo: Photo) {
        if (photo.selected) {
            unselectPhoto(photo)
        } else {
            selectPhoto(photo)
        }
    }

    private fun selectPhoto(photo: Photo) {
        photo.selected = true
        selectedPhotos.add(photo)
        selectedPhotosCounter.value++
        listPhotos[listPhotos.indexOf(photo)] = photo
    }

    private fun unselectPhoto(photo: Photo) {
        photo.selected = false
        selectedPhotos.remove(photo)
        selectedPhotosCounter.value--
        listPhotos[listPhotos.indexOf(photo)] = photo
    }

    /**
     * Unselecting all photos
     */
    fun unselectAll() {
        for (photo in listPhotos) {
            unselectPhoto(photo)
        }
        selectedPhotosCounter.value = 0
    }

    /**
     * Saving all selected photos to external storage and unselecting them.
     * Showing snackbar on UI.
     */
    fun savePhotos() {
        viewModelScope.launch {
            mediaSaver.checkPermission().collect {
                if (it) {
                    for (photo in selectedPhotos) {
                        mediaSaver.apply {
                            downloadPhoto(photo.url)
                        }
                    }
                    _screenEventFlow.emit(
                        SearchScreenEvents.ShowSnackbar(SNACKBAR_TEXT)
                    )
                } else {
                    _screenEventFlow.emit(
                        SearchScreenEvents.ShowSnackbar(PERMISSION_DENIED)
                    )
                }
            }
            unselectAll()
        }
    }
}
