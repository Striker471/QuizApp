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
fun QuizCard(
    title: String,
    imageUrl: String? = null,
    userName: String,
    views: Int = 0,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .size(160.dp, 210.dp)
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Box(
                modifier = Modifier
                    .weight(6.2f)
                    .fillMaxWidth()
            ) {
                imageUrl?.let {
                    LoadingImage(
                        imageUrl = imageUrl,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                    )
                } ?: Image(
                    painter = painterResource(R.drawable.image_dog),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            }
            Box(
                modifier = Modifier
                    .weight(3.8f)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = title,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(horizontal = 8.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = stringResource(R.string.views) + ": " + views,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 10.sp,
                        lineHeight = 15.sp,
                        overflow = TextOverflow.Ellipsis,
                        color = colorResource(R.color.grey_text),
                        modifier = Modifier
                            .padding(start = 4.dp)
                    )
                    Text(
                        text = userName,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp)
                            .wrapContentWidth(Alignment.Start),
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 10.sp,
                        lineHeight = 15.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                }
            }
        }
    }
}