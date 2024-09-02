package com.osama.compose.ads

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.osama.compose.ads.Screens.HomeScreen
import com.osama.compose.ads.Screens.SecondScreen

@Composable
fun NavRoutes(modifier: Modifier = Modifier) {
    val navController= rememberNavController()

    NavHost(navController =navController,
        startDestination = DestinationRoutes.MainHome.route){
        composable(DestinationRoutes.MainHome.route){
            HomeScreen(
                onSecond = {
                    navController.navigate(DestinationRoutes.SecondScreen.route)
                }
            )
        }

        composable(DestinationRoutes.SecondScreen.route){
            SecondScreen()
        }
    }

}