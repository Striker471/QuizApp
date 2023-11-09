package com.example.quizapp.feature.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.quizapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAnswerCard(
    width: Dp,
    onClick: () -> Unit
) {


    ElevatedCard(
        onClick = onClick,
        modifier = Modifier
            .height(100.dp)
            .width(width),
        colors = CardDefaults.elevatedCardColors(
            containerColor = colorResource(R.color.image_card)
        ),
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Image(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
            )
            Text(
                text = stringResource(R.string.add_answear),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}