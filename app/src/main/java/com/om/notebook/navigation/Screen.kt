package com.om.notebook.navigation

sealed class Screen(val route: String){
    object Splash: Screen("splash")
    object Login: Screen("login")
    object Register: Screen("register")
    object Forgot: Screen("forgot")
    object Home: Screen("home")

    object AddNote: Screen("addNote")

}