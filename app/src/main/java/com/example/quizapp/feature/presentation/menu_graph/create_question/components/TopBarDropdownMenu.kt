package com.example.quizapp.feature.presentation.menu_graph.create_question.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.example.quizapp.R

@Composable
fun TopBarDropdownMenu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    onDeleteClick: () -> Unit,
    onFinishClick: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss,
    ) {

        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(R.string.delete),
                )
            },
            colors = MenuDefaults.itemColors(
                textColor = Color.Red,
                leadingIconColor = Color.Red
            ),
            onClick = onDeleteClick,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null
                )
            }
        )
        DropdownMenuItem(
            text = { Text(stringResource(R.string.finish)) },
            onClick = onFinishClick,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = null
                )
            }
        )
    }
}