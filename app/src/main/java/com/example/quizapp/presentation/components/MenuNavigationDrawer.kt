package com.example.quizapp.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.quizapp.presentation.model.NavigationItem

@Composable
fun MenuNavigationDrawer(
    navController: NavController,
    drawerState: DrawerState,
    bodyContent: @Composable (PaddingValues) -> Unit,
    itemsList: List<NavigationItem>
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
//    val route = navController.currentDestination
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                itemsList.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        label = {
                                Text(text = item.title)
                        },
                        selected = item.route == currentRoute ,
                        onClick = { /*TODO*/ })
                }
            }
        }) {

    }


}