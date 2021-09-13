package com.fatec.serieshankbookcompose.ui.theme

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.fatec.serieshankbookcompose.ui.SharedViewModel
import com.fatec.serieshankbookcompose.ui.component.CustomBottomBar
import com.fatec.serieshankbookcompose.ui.component.CustomDrawer
import com.fatec.serieshankbookcompose.ui.component.CustomTopAppBar
import com.fatec.serieshankbookcompose.ui.screen.Screen
import kotlinx.coroutines.CoroutineScope

@SuppressLint("ConflictingOnColor")
private val DarkColorPalette = darkColors(
    primary = BlackC,
    primaryVariant = BlackF,
    onPrimary = Color.White,
    secondary = BlackF,
    secondaryVariant = Color.Black,
    onSecondary = BlackA,
    error = RedErrorDark,
    onError = RedErrorLight,
    background = Color.Black,
    onBackground = Color.White,
    surface = BlackC,
    onSurface = BlackF,
)

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
    primary = GreenA,
    primaryVariant = GreenB,
    onPrimary = Color.White,
    secondary = GreenD,
    secondaryVariant = GreenA,
    onSecondary = Color.White,
    error = RedErrorDark,
    onError = RedErrorLight,
    background = BlackA,
    onBackground = BlackF,
    surface = GreenA,
    onSurface = Color.White,

)

@SuppressLint("ConflictingOnColor")
private val AlternativeColorPalette = Colors(
    primary = GreenE,
    primaryVariant = GreenB,
    onPrimary = Color.White,
    secondary = GreenC,
    secondaryVariant = GreenE,
    onSecondary = Color.Black,
    error = RedErrorDark,
    onError = RedErrorLight,
    background = GreenBG,
    onBackground = Color.Black,
    surface = GreenE,
    onSurface = Color.White,
    isLight = false
)

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun SeriesHankbookComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    topBar: Boolean = true,
    bottomBar: Boolean = true,
    drawer: Boolean = true,
    navController: NavController,
    scope: CoroutineScope,
    currentRoute: String? = null,
    itemsBottom: List<Screen>? = null,
    navStart: Screen? = null,
    headerContent: @Composable () -> Unit = {},
    sharedViewModel: SharedViewModel,
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes

    ){
        Scaffold(
            topBar = {
                if (topBar) CustomTopAppBar(
                    scaffoldState = scaffoldState,
                    scope = scope,
                    headerContent = headerContent,
                    drawer = drawer
                )
            },
            bottomBar = {
                if (bottomBar) CustomBottomBar(
                    itemsBottom = itemsBottom!!,
                    navController = navController,
                    navStart = navStart!!,
                    currentRoute = currentRoute
                )
            },
            drawerContent = {
                if (drawer) CustomDrawer(
                    navController = navController,
                    sharedViewModel = sharedViewModel,
                    scaffoldState = scaffoldState
                )
            },
            scaffoldState = scaffoldState
        ) { innerPadding->
            Box(modifier = Modifier.padding(innerPadding)) {
                content()
            }
        }
    }
}