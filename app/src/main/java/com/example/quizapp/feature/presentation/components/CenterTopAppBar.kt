package com.example.quizapp.feature.presentation.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.quizapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenterTopAppBar(
    titleText: String,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(R.color.topBar)
        ),
        title = {
            Text(
                text = titleText,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = navigationIcon,
        actions = actions,
    )
}