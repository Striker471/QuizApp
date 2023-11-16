package com.example.quizapp.feature.presentation.menu_graph.menu.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.quizapp.R

@Composable
fun LogoutDropdownMenu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    onLogoutCLick: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss
    ) {
        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(R.string.logout),
                )
            },
            onClick = onLogoutCLick,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Logout,
                    contentDescription = null
                )
            }
        )
    }
}