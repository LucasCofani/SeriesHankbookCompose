package com.fatec.serieshankbookcompose.ui.screen.splashscreen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fatec.serieshankbookcompose.ui.screen.Screen
import com.fatec.serieshankbookcompose.ui.theme.GreenBG
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember{
        Animatable(0f)
    }
    LaunchedEffect(key1 = true){
        scale.animateTo(
            targetValue = 1.5f,
            animationSpec = tween(
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        delay(3000L)
        navController.navigate(Screen.LoginScreen.route){
            popUpTo(Screen.SplashScreen.route){
                inclusive
            }
            launchSingleTop = true
        }
    }
    Column(
        modifier = Modifier.fillMaxSize().background(GreenBG),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "SeriesHandbook",
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(CenterHorizontally).scale(scale.value)
        )
    }
}