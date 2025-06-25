package com.cardes.designsystem.component

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction

@Composable
fun FeeBeeTextInput(
    title: String,
    text: String,
    onTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    enabled: Boolean = true,
) {
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        modifier = modifier,
        enabled = enabled,
        value = text,
        label = {
            Text(text = title)
        },
        onValueChange = onTextChanged,
        readOnly = readOnly,
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    )
}
