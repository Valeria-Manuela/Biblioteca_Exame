package com.example.biblioteca.Navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.biblioteca.ui.Telas.BookDetailScreen
import com.example.biblioteca.ui.Telas.HomeScreen
import com.example.biblioteca.ui.Telas.LoginScreen
import com.example.biblioteca.ui.Telas.ReadingGoalsScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {
        composable(Routes.LOGIN) {
            LoginScreen(onLoginSuccess = {
                navController.navigate(Routes.HOME) {
                    popUpTo(Routes.LOGIN) { inclusive = true }
                }
            })
        }

        composable(Routes.HOME) {
            HomeScreen(navController = navController)
        }

        composable(
            route = "book_details/{bookTitle}/{bookCover}",
            arguments = listOf(
                navArgument("bookTitle") { type = NavType.StringType },
                navArgument("bookCover") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("bookTitle") ?: ""
            val cover = backStackEntry.arguments?.getString("bookCover") ?: ""

            BookDetailScreen(
                bookTitle = java.net.URLDecoder.decode(title, "UTF-8"),
                bookCoverUrl = java.net.URLDecoder.decode(cover, "UTF-8"),
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("reading_goals") {
            ReadingGoalsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}