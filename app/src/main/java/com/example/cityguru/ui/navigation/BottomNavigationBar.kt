package com.example.cityguru.ui.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cityguru.R
import com.example.cityguru.ui.theme.Secondary
import com.example.cityguru.ui.theme.Purple

@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    val items = BottomNavItem.items

    NavigationBar(
        modifier = Modifier.height(64.dp),
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                label = null,
                icon = {
                    when (item) {
                        is BottomNavItem.Search -> Icon(
                            painter = painterResource(R.drawable.ic_search),
                            contentDescription = item.title
                        )
                        is BottomNavItem.Map -> Icon(
                            painter = painterResource(R.drawable.ic_map),
                            contentDescription = item.title
                        )
                    }
                },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Purple,
                    unselectedIconColor = Secondary,
                )
            )
        }
    }
}

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}