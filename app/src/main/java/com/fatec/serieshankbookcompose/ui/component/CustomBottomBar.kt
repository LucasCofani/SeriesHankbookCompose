package com.fatec.serieshankbookcompose.ui.component

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.fatec.serieshankbookcompose.ui.screen.Screen


@Composable
fun CustomBottomBar(
    itemsBottom: List<Screen>,
    navController: NavController,
    inclusive : Boolean = true,
    navStart: Screen,
    currentRoute: String?,
) {
    BottomNavigation {
        itemsBottom.forEach { screen ->
            BottomNavigationItem(
                icon =  screen.icon ,
                label = { Text(screen.display) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route)
                    {
                        popUpTo(navStart.route) {
                            this.inclusive = inclusive
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}