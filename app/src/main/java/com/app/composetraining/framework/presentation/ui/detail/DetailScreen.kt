package com.app.composetraining.framework.presentation.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.composetraining.R
import com.skydoves.landscapist.glide.GlideImage

/**
 * contains a specific photo and a description of the item selected by the user
 *
 * @param (event) on click go back to the list of photos
 * @param (state) viewModel
 */
@Composable
fun DetailScreen(
    onClickSeeAllPhotos: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val photo = viewModel.currentPhoto.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        GlideImage(
            imageModel = photo.url,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            loading = {
                CircularProgressIndicator()
            },
            failure = {
                Image(
                    bitmap = ImageBitmap.imageResource(R.drawable.no_photo),
                    contentDescription = "Can't load photo"
                )
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        photo.title?.let { text ->
            Text(
                text = text,
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        ElevatedButton(onClick = onClickSeeAllPhotos) {
            Text(
                text = LocalContext.current.getString(
                    com.app.composetraining.R.string.button_back_text
                ),
                modifier = Modifier
                    .weight(1f)
                    .width(200.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}
