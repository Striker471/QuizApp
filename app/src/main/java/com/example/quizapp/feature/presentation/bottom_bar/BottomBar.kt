package com.example.quizapp.feature.presentation.bottom_bar


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.FolderShared
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.FolderShared
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.quizapp.R
import com.example.quizapp.feature.presentation.model.BottomNavigationItem
import com.example.quizapp.feature.presentation.util.Screen

@Composable
fun BottomBar(
    navController: NavController,
//    viewModel: BottomBarSharedViewModel = hiltViewModel()
) {
//    val navBackStackEntry by navController.currentBackStackEntryAsState()
//    val currentRoute = navBackStackEntry?.destination?.route

    val currentRoute = navController.currentDestination?.route

    val items = listOf(
        BottomNavigationItem(
            title = stringResource(R.string.home),
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            hasNews = false,
            route = Screen.MenuScreen.route
        ),
        BottomNavigationItem(
            title = stringResource(R.string.library),
            selectedIcon = Icons.Filled.Folder,
            unselectedIcon = Icons.Outlined.Folder,
            hasNews = false,
            route = Screen.LibraryScreen.route
        ),
        BottomNavigationItem(
            title = stringResource(R.string.my_quizzes),
            selectedIcon = Icons.Filled.FolderShared,
            unselectedIcon = Icons.Outlined.FolderShared,
            hasNews = false,
            route = Screen.MyQuizzesScreen.route
        ),
        BottomNavigationItem(
            title = stringResource(R.string.profile),
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
            hasNews = false,
            route = Screen.ProfileScreen.route
        ),
    )

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        launchSingleTop = true
                    }
                },
                label = {
                    Text(text = item.title)
                },
                icon = {
                    Icon(
                        imageVector = if (currentRoute == item.route) {
                            item.selectedIcon
                        } else item.unselectedIcon,
                        contentDescription = item.title
                    )
                })
        }
    }
}

