package com.example.quizapp.feature.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter

@Composable
fun LoadingImage(
    imageUrl: String,
    contentDescription: String? = null,
    modifier: Modifier = Modifier
) {
    var imageState by remember { mutableStateOf<AsyncImagePainter.State>(AsyncImagePainter.State.Empty) }

    AsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        modifier = Modifier,
        contentScale = ContentScale.FillBounds,
        onState = { imageState = it }
    )

    if (imageState is AsyncImagePainter.State.Loading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}