package com.example.quizapp.feature.presentation.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizapp.R

@Composable
fun QuizCardMyProfile(
    title: String = "Quizw",
    quizUrl: String? = null,
    views: Int = 0,
    onClick: () -> Unit = { }
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .clickable(
                onClick = onClick
            )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            quizUrl?.let {
                LoadingImage(
                    imageUrl = it,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(4f / 3f),
                )
            } ?: Image(
                painter = painterResource(R.drawable.image_dog),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(4f / 3f),
                contentScale = ContentScale.FillBounds
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = title,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(horizontal = 8.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    textAlign = TextAlign.Center,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = stringResource(R.string.views) + ": " + views,
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 12.sp,
                    lineHeight = 18.sp,
                    overflow = TextOverflow.Ellipsis,
                    color = colorResource(R.color.grey_text),
                    modifier = Modifier
                        .padding(start = 4.dp)
                )
                Spacer(modifier = Modifier.height(2.dp))
            }
        }
    }
}