package com.fatec.serieshankbookcompose.ui

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.fatec.serieshankbookcompose.ui.component.TopBarTitle
import com.fatec.serieshankbookcompose.ui.screen.LoginScreen
import com.fatec.serieshankbookcompose.ui.screen.Screen
import com.fatec.serieshankbookcompose.ui.screen.aboutscreen.AboutScreen
import com.fatec.serieshankbookcompose.ui.screen.moviedetailscreen.MovieDetailScreen
import com.fatec.serieshankbookcompose.ui.screen.moviediscoveryscreen.MovieDiscoveryScreen
import com.fatec.serieshankbookcompose.ui.screen.moviehistoryscreen.MovieHistoryScreen
import com.fatec.serieshankbookcompose.ui.screen.movielistscreen.MovieListScreen
import com.fatec.serieshankbookcompose.ui.screen.seriedetailscreen.SerieDetailScreen
import com.fatec.serieshankbookcompose.ui.screen.seriediscoveryscreen.SerieDiscoveryScreen
import com.fatec.serieshankbookcompose.ui.screen.seriehistoryscreen.SerieHistoryScreen
import com.fatec.serieshankbookcompose.ui.screen.serielistscreen.SerieListScreen
import com.fatec.serieshankbookcompose.ui.screen.splashscreen.SplashScreen
import com.fatec.serieshankbookcompose.ui.theme.SeriesHankbookComposeTheme
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val sharedViewModel: SharedViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.N)
    @ExperimentalComposeUiApi
    @ExperimentalMaterialApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Função de controle de login
        val signInLauncher = registerForActivityResult(
            FirebaseAuthUIActivityResultContract()
        ) { res ->
            if (res.resultCode == RESULT_OK) {
                sharedViewModel.getUserEmail()
            } else {
                sharedViewModel.loginAttempt()
            }
        }

        setContent {
            val navController = rememberNavController()
            val scope = rememberCoroutineScope()
            NavHost(
                navController = navController,
                startDestination = Screen.SplashScreen.route
            ) {
                composable(route = Screen.SplashScreen.route) {
                    SplashScreen(navController = navController)
                }
                composable(route = Screen.LoginScreen.route) {
                    LoginScreen(
                        navController = navController,
                        signInLauncher = signInLauncher::launch,
                        viewModel = sharedViewModel
                    )
                }
                composable(route = Screen.AboutScreen.route) {
                    AboutScreen()
                }
                composable(route = Screen.SerieDiscoveryScreen.route) {
                    SeriesHankbookComposeTheme(
                        topBar = true,
                        bottomBar = true,
                        drawer = true,
                        navController = navController,
                        itemsBottom = listOf(
                            Screen.SerieDiscoveryScreen,
                            Screen.SerieListScreen,
                            Screen.SerieHistoryScreen,
                        ),
                        navStart = Screen.SerieDiscoveryScreen,
                        currentRoute = Screen.SerieDiscoveryScreen.route,
                        scope = scope,
                        headerContent = { TopBarTitle(text = Screen.SerieDiscoveryScreen.display) },
                        sharedViewModel = sharedViewModel
                    )
                    {
                        SerieDiscoveryScreen(
                            navController = navController,
                            sharedViewModel = sharedViewModel
                        )
                    }
                }
                composable(route = Screen.SerieListScreen.route) {
                    SeriesHankbookComposeTheme(
                        topBar = true,
                        bottomBar = true,
                        drawer = true,
                        navController = navController,
                        itemsBottom = listOf(
                            Screen.SerieDiscoveryScreen,
                            Screen.SerieListScreen,
                            Screen.SerieHistoryScreen,
                        ),
                        navStart = Screen.SerieDiscoveryScreen,
                        currentRoute = Screen.SerieListScreen.route,
                        scope = scope,
                        headerContent = { TopBarTitle(text = Screen.SerieListScreen.display) },
                        sharedViewModel = sharedViewModel
                    )
                    {
                        SerieListScreen()
                    }
                }
                composable(route = Screen.SerieHistoryScreen.route) {
                    SeriesHankbookComposeTheme(
                        topBar = true,
                        bottomBar = true,
                        drawer = true,
                        navController = navController,
                        itemsBottom = listOf(
                            Screen.SerieDiscoveryScreen,
                            Screen.SerieListScreen,
                            Screen.SerieHistoryScreen,
                        ),
                        navStart = Screen.SerieDiscoveryScreen,
                        currentRoute = Screen.SerieHistoryScreen.route,
                        scope = scope,
                        headerContent = { TopBarTitle(text = Screen.SerieHistoryScreen.display) },
                        sharedViewModel = sharedViewModel
                    )
                    {
                        SerieHistoryScreen()
                    }
                }
                composable(
                    route = Screen.SerieDetailScreen.route + "/{id}",
                    arguments = listOf(
                        navArgument("id") {
                            type = NavType.IntType
                        }
                    )
                ) {
                    SeriesHankbookComposeTheme(
                        topBar = false,
                        bottomBar = false,
                        drawer = true,
                        navController = navController,
                        scope = scope,
                        sharedViewModel = sharedViewModel
                    )
                    {
                        SerieDetailScreen(
                            navController = navController,
                            id = it.arguments?.getInt("id")!!
                        )
                    }
                }
                composable(route = Screen.MovieDiscoveryScreen.route) {
                    SeriesHankbookComposeTheme(
                        topBar = true,
                        bottomBar = true,
                        drawer = true,
                        navController = navController,
                        itemsBottom = listOf(
                            Screen.MovieDiscoveryScreen,
                            Screen.MovieListScreen,
                            Screen.MovieHistoryScreen,
                        ),
                        navStart = Screen.MovieDiscoveryScreen,
                        currentRoute = Screen.MovieDiscoveryScreen.route,
                        scope = scope,
                        headerContent = { TopBarTitle(text = Screen.MovieDiscoveryScreen.display) },
                        sharedViewModel = sharedViewModel
                    )
                    {
                        MovieDiscoveryScreen(
                            navController = navController,
                            sharedViewModel = sharedViewModel
                        )
                    }
                }
                composable(route = Screen.MovieListScreen.route) {
                    SeriesHankbookComposeTheme(
                        topBar = true,
                        bottomBar = true,
                        drawer = true,
                        navController = navController,
                        itemsBottom = listOf(
                            Screen.MovieDiscoveryScreen,
                            Screen.MovieListScreen,
                            Screen.MovieHistoryScreen,
                        ),
                        navStart = Screen.MovieDiscoveryScreen,
                        currentRoute = Screen.MovieListScreen.route,
                        scope = scope,
                        headerContent = { TopBarTitle(text = Screen.MovieListScreen.display) },
                        sharedViewModel = sharedViewModel
                    )
                    {
                        MovieListScreen()
                    }
                }
                composable(route = Screen.MovieHistoryScreen.route) {
                    SeriesHankbookComposeTheme(
                        topBar = true,
                        bottomBar = true,
                        drawer = true,
                        navController = navController,
                        itemsBottom = listOf(
                            Screen.MovieDiscoveryScreen,
                            Screen.MovieListScreen,
                            Screen.MovieHistoryScreen,
                        ),
                        navStart = Screen.MovieDiscoveryScreen,
                        currentRoute = Screen.MovieHistoryScreen.route,
                        scope = scope,
                        headerContent = { TopBarTitle(text = Screen.MovieHistoryScreen.display) },
                        sharedViewModel = sharedViewModel
                    )
                    {
                        MovieHistoryScreen()
                    }
                }
                composable(
                    route = Screen.MovieDetailScreen.route + "/{id}",
                    arguments = listOf(
                        navArgument("id") {
                            type = NavType.IntType
                        }
                    )
                ) {
                    SeriesHankbookComposeTheme(
                        topBar = false,
                        bottomBar = false,
                        drawer = true,
                        navController = navController,
                        scope = scope,
                        sharedViewModel = sharedViewModel
                    )
                    {
                        MovieDetailScreen(
                            navController = navController,
                            id = it.arguments?.getInt("id")!!
                        )
                    }
                }
            }
        }
    }
}


