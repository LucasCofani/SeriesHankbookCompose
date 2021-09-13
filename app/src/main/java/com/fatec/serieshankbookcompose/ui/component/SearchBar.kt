package com.fatec.serieshankbookcompose.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {}
) {
    var text by remember {
        mutableStateOf("")
    }
    var isHintDisplayed by remember {
        mutableStateOf(true)
    }

    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(Color.White, CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .onFocusChanged {
                    isHintDisplayed = !it.isFocused && text.isNullOrBlank()
                }
        )
        if(isHintDisplayed) {
            Text(
                text = hint,
                color = Color.LightGray,
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            )
        }
    }
}
@ExperimentalComposeUiApi
@Composable
fun SearchField(
    modifier: Modifier = Modifier,
    hint: String,
    onSearch: (String) -> Unit = {},
    eventOnChangeTrigger:Boolean = false,
    eventOnDoneTrigger:Boolean = true,
    eventOnCleanTrigger:Boolean = true,
) {
    var text by remember {
        mutableStateOf("")
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        modifier = modifier,
        value = text,
        onValueChange = {
            text = it
            if (eventOnChangeTrigger)
                onSearch(text)
        },
        label = { Text(text = hint) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                if (eventOnDoneTrigger) {
                    keyboardController?.hide()
                    onSearch(text)
                }
            },
        ),
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search Icon",modifier = Modifier.clickable {
            keyboardController?.hide()
            onSearch(text)
        }) },
        trailingIcon = {
            if (!text.isNullOrEmpty())
            Icon(
                Icons.Filled.Close,
                contentDescription = "Cancel",
                modifier = Modifier.clickable {
                    keyboardController?.hide()
                    text = ""
                    if (eventOnCleanTrigger)
                        onSearch(text)
                })
        },
        textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
        colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface),
        singleLine = true
    )
}