package com.om.notebook.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.om.notebook.ui.AddNoteScreen
import com.om.notebook.ui.EditNoteScreen
import com.om.notebook.ui.ForgotPasswordScreen
import com.om.notebook.ui.HomeScreen
import com.om.notebook.ui.LoginScreen
import com.om.notebook.ui.RegisterScreen
import com.om.notebook.ui.SplashScreen

@Composable
fun NavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route   // 🔥 best practice
    ) {

        composable(Screen.Splash.route) {
            SplashScreen(navController)
        }
        composable(Screen.Login.route) {
            LoginScreen(navController)
        }
        composable(Screen.Register.route) {
            RegisterScreen(navController)
        }
        composable(Screen.Forgot.route) {
            ForgotPasswordScreen()
        }
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }

        composable(Screen.AddNote.route) {
            AddNoteScreen(navController)
        }
        composable("editNote/{id}") { backStackEntry ->

            val id = backStackEntry.arguments?.getString("id") ?: ""

            EditNoteScreen(
                navController = navController,
                noteId = id
            )
        }

    }
}