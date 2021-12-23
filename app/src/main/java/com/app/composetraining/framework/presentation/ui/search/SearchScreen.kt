package com.app.composetraining.framework.presentation.ui.search

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.composetraining.R
import com.app.composetraining.buisiness.domain.models.Photo
import com.app.composetraining.framework.presentation.ui.search.components.PhotosList
import com.app.composetraining.framework.presentation.ui.search.components.SearchInputText
import com.app.composetraining.framework.presentation.ui.search.components.SearchButton
import kotlinx.coroutines.flow.collectLatest

/**
 * Composable component that shows up search results
 *
 * @param onClickSeeDetail (event) starting executing request with user input
 * @param viewModel (state)
 */
@Composable
fun SearchScreen(
    onClickSeeDetail: (Int?) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val isLoading = viewModel.isLoading.value
    val isError = viewModel.isError.value
    val photosList = viewModel.listPhotos
    val selectedPhotosCount = viewModel.selectedPhotosCounter.value
    val scaffoldState = rememberScaffoldState()

    val checkClick = { photo: Photo ->
        if (selectedPhotosCount > 0) {
            viewModel.checkIsSelected(photo)
        } else {
            onClickSeeDetail(photo.primaryKeyId)
        }
    }

    LaunchedEffect(key1 = true, block = {
        viewModel.screenEventFlow.collectLatest { event ->
            when (event) {
                is SearchScreenEvents.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    })

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = { shackBarHostState ->
            SnackbarHost(shackBarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    contentColor = Color.White,
                )
            }
        }
    ) {
        Column {
            if (selectedPhotosCount > 0) {
                SelectingItemsTopSection(
                    selectedItemsCount = selectedPhotosCount,
                    onSave = { viewModel.savePhotos() },
                    onCancel = { viewModel.unselectAll() }
                )
            } else {
                SearchEntryInput(viewModel.searchQuery.value) { searchQuery ->
                    viewModel.onEvent(SearchEvent.NewSearchEvent(searchQuery))
                }
            }
            PhotosList(
                page = viewModel.page.value,
                itemsList = photosList,
                loading = isLoading,
                error = isError,
                onTriggerNextPage = { viewModel.onEvent(SearchEvent.NextPageEvent) },
                onChangeScrollPosition = viewModel::setListScrollPosition,
                onItemClicked = checkClick,
                onLongItemClick = viewModel::checkIsSelected
            )

        }
    }
}

/**
 * Stateful composable that allow to start new Search
 *
 * @param searchQuery (state) contains users input
 * @param buttonText (state) text to display on button
 * @param onSearch (event) starts photo research
 */
@Composable
fun SearchEntryInput(
    searchQuery: String?,
    buttonText: String = LocalContext.current.getString(R.string.button_search_text),
    onSearch: (String) -> Unit
) {
    val (text, onTextChange) = if (searchQuery == null) {
        rememberSaveable { mutableStateOf("") }
    } else {
        rememberSaveable { mutableStateOf(searchQuery) }
    }
    val submit = {
        if (text.isNotBlank()) {
            onSearch(text)
        }
    }
    SearchInput(
        text = text,
        onTextChange = onTextChange,
        submit = submit
    ) {
        SearchButton(onClick = submit, text = buttonText, enabled = text.isNotBlank())
    }
}

/**
 * Stateless input composable for user request.
 *
 * @param text (state) current text of the item
 * @param onTextChange (event) request the text change
 * @param submit (event) notify the caller that the user has submitted with [ImeAction.Done]
 * @param buttonSlot (slot) slot for providing buttons next to the text
 */
@Composable
fun SearchInput(
    text: String,
    onTextChange: (String) -> Unit,
    submit: () -> Unit,
    buttonSlot: @Composable () -> Unit,
) {
    Column {
        Row(
            Modifier
                .padding(horizontal = 16.dp)
                .padding(vertical = 8.dp)
                .height(IntrinsicSize.Min)
        ) {
            SearchInputText(
                text = text,
                onTextChange = onTextChange,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                onImeAction = submit
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(Modifier.align(Alignment.CenterVertically)) { buttonSlot() }
        }
    }
}

/**
 * Stateless component shows how many items user selected
 *
 * @param selectedItemsCount (num) number of selected items
 * @param onSave (event) saves all selected items
 * @param onCancel () removes highlighting from selected items
 */
@Composable
fun SelectingItemsTopSection(
    selectedItemsCount: Int,
    onSave: () -> Unit,
    onCancel: () -> Unit,
) {
    Row(Modifier.padding(10.dp)) {
        Text(
            text = "${LocalContext.current.getString(R.string.text_selected)}$selectedItemsCount",
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f)
                .padding(16.dp)
                .fillMaxWidth()
        )
        TextButton(onClick = onSave) {
            Text(text = LocalContext.current.getString(R.string.button_save_text))
        }
        TextButton(onClick = onCancel) {
            Text(text = LocalContext.current.getString(R.string.button_cancel_text))
        }
    }
}
