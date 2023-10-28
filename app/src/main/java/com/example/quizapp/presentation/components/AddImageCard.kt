package com.example.quizapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddImageCard(

) {
    ElevatedCard(
        onClick ={},
        modifier = Modifier
            .height(182.dp)
            .fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = colorResource(R.color.image_card)
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Image(
                imageVector = Icons.Filled.Image,
                contentDescription = null,
                modifier = Modifier
                    .size(82.dp)
            )
            Text(
                text = stringResource(R.string.add_image),
                fontSize = 16.sp
            )
        }
    }
}