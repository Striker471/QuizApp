package com.example.quizapp.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.example.quizapp.R

@Composable
fun MainOutlinedTextField(
    text: String,
    onValueChange: (String) -> Unit,
    label: String = "",
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
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth(0.7f),
        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = colorResource(R.color.purple))
    )
}