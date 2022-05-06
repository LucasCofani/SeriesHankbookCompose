package com.fatec.serieshankbookcompose.ui.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fatec.serieshankbookcompose.ui.SharedViewModel
import com.fatec.serieshankbookcompose.ui.screen.Screen

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun CustomDrawer(
    navController: NavController,
    sharedViewModel: SharedViewModel,
    scaffoldState: ScaffoldState
) {
    sharedViewModel.getUserEmail()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp, bottom = 10.dp)
    ) {
        Text(
            text = "Você está conectado(a) com: ${sharedViewModel.loggedUser.value}",
            fontSize = 18.sp,
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Justify
        )

        Divider()
        ListItem(
            text = { Text("Series") },
            modifier = Modifier.clickable {
                navController.navigate(Screen.SerieDiscoveryScreen.route){
                    launchSingleTop = true
                }
            }
        )
        Divider()
        ListItem(
            text = { Text("Filmes") },
            modifier = Modifier.clickable {
                navController.navigate(Screen.MovieDiscoveryScreen.route){
                    launchSingleTop = true
                }
            }
        )
        Divider()
        ListItem(
            text = { Text("Sobre") },
            modifier = Modifier.clickable {
                navController.navigate(Screen.AboutScreen.route)
            }
        )
        Divider()
        ListItem(
            text = { Text("Logout") },
            modifier = Modifier.clickable {
                sharedViewModel.logout()
            }
        )
        Divider()

    }
}