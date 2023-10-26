package com.example.quizapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizapp.R

@Composable
fun OutlinedButtonWithImage(
    onClick: () -> Unit,
    text: String,
    icon: Int,
) {
    OutlinedButton(
        onClick = {
            onClick()
        },
        modifier = Modifier
            .fillMaxWidth(0.91f)
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.lightsurface),
            contentColor = colorResource(R.color.onlightsurface),
        ),
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = Color.Unspecified,
        )
        Text(
            text = text,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
                .offset(x = (-12).dp),
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            )
        )
    }
}