package com.app.composetraining.framework.presentation.ui.search.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import com.app.composetraining.framework.presentation.ui.search.SearchScreen

/**
 * Material [OutlinedTextField] for inputting a user search query.
 *
 * @param text (state) current text to display
 * @param onTextChange (event) request the text change state
 * @param modifier the modifier for this element
 * @param onImeAction (event) notify caller of [ImeAction.Done] events
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchInputText(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onImeAction: () -> Unit = {}
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        value = text,
        onValueChange = onTextChange,
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
        maxLines = 1,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            onImeAction()
            keyboardController?.hide()
        }),
        modifier = modifier,
    )
}

/**
 * Material [TextButton] for [SearchScreen]
 *
 * @param onClick (event) notify caller with onClick event
 * @param text button text
 * @param modifier modifier for button
 * @param enabled enable or disable the button
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    TextButton(
        onClick = {
            onClick()
            keyboardController?.hide()
        },
        enabled = enabled,
        modifier = modifier
    ) {
        Text(text)
    }
}
