package com.app.composetraining.framework.presentation.ui.search.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.app.composetraining.R
import com.app.composetraining.buisiness.domain.models.Photo
import com.app.composetraining.framework.presentation.utils.PHOTOS_PER_PAGE
import com.skydoves.landscapist.glide.GlideImage

/**
 * Composable, that displays list of photos
 *
 * @param itemsList items to show
 * @param onItemClicked (event) notify caller that the row was clicked
 * @param onLongItemClick (event) notify caller that the row was long-clicked
 */
@Composable
fun PhotosList(
    page: Int,
    itemsList: List<Photo>,
    loading: Boolean,
    error: Boolean,
    onTriggerNextPage: () -> Unit,
    onChangeScrollPosition: (Int) -> Unit,
    onItemClicked: (Photo) -> Unit,
    onLongItemClick: (Photo) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(1f),
        contentAlignment = Alignment.Center
    ) {
        if (loading && itemsList.isEmpty()) {
            CircularProgressIndicator()
        } else if (error) {
            Text(text = LocalContext.current.getString(R.string.error_no_internet))
        } else if (itemsList.isEmpty()) {
            EmptyList()
        } else {
            LazyColumn(
                contentPadding = PaddingValues(top = 8.dp),
            ) {
                itemsIndexed(items = itemsList) { index, photo ->
                    onChangeScrollPosition(index)
                    if ((index + 1) >= (page * PHOTOS_PER_PAGE) && !loading) {
                        onTriggerNextPage()
                    }
                    PhotoCard(
                        photo = photo,
                        onItemClicked = { onItemClicked(photo) },
                        onLongItemClick = { onLongItemClick(photo) },
                    )
                }
            }
            if (loading) {
                CircularProgressIndicator()
            }
        }
    }
}


/**
 * Stateless composable that displays a full-width [Photo].
 *
 * @param photo to show
 * @param onItemClicked (event) notify caller that the row was clicked
 * @param onLongItemClick (event) notify caller that the row was long-clicked
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhotoCard(
    photo: Photo,
    onItemClicked: (Photo) -> Unit,
    onLongItemClick: (Photo) -> Unit,
) {
    val cardBackground = if (photo.selected) Color.Blue else Color.White
    Card(
        backgroundColor = cardBackground,
        modifier = Modifier.padding(10.dp),
        elevation = 8.dp
    ) {
        Column(Modifier.padding(10.dp)) {
            GlideImage(
                imageModel = photo.url,
                loading = {
                    CircularProgressIndicator()
                },
                failure = {
                    Image(
                        bitmap = ImageBitmap.imageResource(R.drawable.no_photo),
                        contentDescription = "Can't load photo"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .combinedClickable(
                        enabled = true,
                        onClick = { onItemClicked(photo) },
                        onLongClick = {
                            onLongItemClick(photo)
                        }
                    )
            )
        }
    }
}

/**
 * Stateless composable, notify caller that nothing to show
 */
@Composable
fun EmptyList() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                text = LocalContext.current.getString(R.string.text_empty_cartoon)
            )
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                text = LocalContext.current.getString(R.string.text_empty_list)
            )
        }
    }
}
