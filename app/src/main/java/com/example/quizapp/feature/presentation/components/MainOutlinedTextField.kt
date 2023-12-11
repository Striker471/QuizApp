package com.example.quizapp.feature.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import com.example.quizapp.R

@Composable
fun MainOutlinedTextField(
    text: String,
    onValueChange: (String) -> Unit,
    label: String = "",
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {

    OutlinedTextField(
        value = text,
        onValueChange = { onValueChange(it) },
        label = {
            Text(
                label,  
                style = TextStyle(fontSize = 12.sp)
            )
        },
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth(0.7f),
        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = colorResource(R.color.purple))
    )
}