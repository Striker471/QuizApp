package com.example.quizapp.feature.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.quizapp.R

@Composable
fun MainActionButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {

    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth(0.83f)
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.purple_button),
            contentColor = colorResource(R.color.onPurpleButton)
        ),
        shape = RoundedCornerShape(20.dp),
        enabled = enabled
        ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}