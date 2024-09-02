package com.osama.compose.ads

sealed class DestinationRoutes(
    val route: String
){
    object MainHome: DestinationRoutes(route = "mainhome")
    object SecondScreen: DestinationRoutes(route = "secondscreen")
}