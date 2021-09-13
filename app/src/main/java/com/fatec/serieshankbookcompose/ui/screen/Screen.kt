package com.fatec.serieshankbookcompose.ui.screen

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.fatec.serieshankbookcompose.R

sealed class Screen(val route : String,val display:String,val icon: @Composable () -> Unit = {}){
    object SplashScreen : Screen("SplashScreen","Splash")
    object LoginScreen: Screen("LoginScreen","Login")
    object AboutScreen: Screen("AboutScreen","Sobre")

    object SerieDiscoveryScreen : Screen("SerieDiscoveryScreen","Descobrir series",{
        Icon(painterResource(R.drawable.ic_grass), contentDescription = "Search Icon")
    })
    object SerieListScreen: Screen("SerieListScreen","Lista de series",{
        Icon(Icons.Filled.List, contentDescription = "Search Icon")
    })
    object SerieHistoryScreen: Screen("SerieHistoryScreen","Hist. de series",{
        Icon(painterResource(R.drawable.ic_history), contentDescription = "Search Icon")
    })

    object MovieDiscoveryScreen : Screen("MovieDiscoveryScreen","Descobrir filmes",{
        Icon(painterResource(R.drawable.ic_grass), contentDescription = "Search Icon")
    })
    object MovieListScreen: Screen("MovieListScreen","Lista de filmes",{
        Icon(Icons.Filled.List, contentDescription = "Search Icon")
    })
    object MovieHistoryScreen: Screen("MovieHistoryScreen","Hist. de filmes",{
        Icon(painterResource(R.drawable.ic_history), contentDescription = "Search Icon")
    })

    object SerieDetailScreen: Screen("SerieDetailScreen","Serie detalhe")
    object MovieDetailScreen: Screen("MovieDetailScreen","Filme detalhe")

}
