package com.fatec.serieshankbookcompose.ui.screen

import android.content.Intent
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.fatec.serieshankbookcompose.ui.SharedViewModel
import kotlin.system.exitProcess

@Composable
fun LoginScreen(
    navController: NavController,
    signInLauncher: (Intent) -> Unit,
    viewModel: SharedViewModel
) {
    var inicio by remember { mutableStateOf(true) }
    var user by remember { viewModel.loggedUser }


    // Se ja estiver logado move para proxima pagina
    if (user.contains("@")){
        // Remove historico de navegação
        GoToMain(navController = navController)
    }else if(user == "desistiu"){
        exitProcess(0)
    }
    else {
        if (inicio && user != "") {
            signInLauncher(viewModel.createLoginIntent())
            inicio = false
        }else if (inicio && user == ""){
            viewModel.getUserEmail()
        }
    }
    
}
fun GoToMain(navController: NavController){
    navController.navigate(Screen.SerieDiscoveryScreen.route, builder = {
        popUpTo(Screen.LoginScreen.route){ inclusive = true}
        launchSingleTop = true
    })
}