package com.example.biblioteca.Navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.biblioteca.Database.BookDao
import com.example.biblioteca.Database.GoalDao
import com.example.biblioteca.Database.UserDao
import com.example.biblioteca.ViewModel.AuthViewModel
import com.example.biblioteca.ui.Telas.*
import java.net.URLDecoder

@Composable
fun AppNavigation(
    bookDao: BookDao,
    goalDao: GoalDao,
    userDao: UserDao
) {
    val navController = rememberNavController()

    val authViewModel: AuthViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {
        composable(Routes.SPLASH) {
            SplashScreen(navController)
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                viewModel = authViewModel,
                onLoginSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Routes.REGISTER)
                }
            )
        }

        composable(Routes.REGISTER) {
            RegisterScreen(
                viewModel = authViewModel,
                onRegisterSuccess = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.REGISTER) { inclusive = true }
                    }
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Routes.HOME) {
            HomeScreen(
                navController = navController,
                bookDao = bookDao
            )
        }

        composable(
            route = "book_details/{bookTitle}/{bookCover}/{bookDesc}",
            arguments = listOf(
                navArgument("bookTitle") { type = NavType.StringType },
                navArgument("bookCover") { type = NavType.StringType },
                navArgument("bookDesc") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("bookTitle") ?: ""
            val cover = backStackEntry.arguments?.getString("bookCover") ?: ""
            val desc = backStackEntry.arguments?.getString("bookDesc") ?: ""

            BookDetailScreen(
                bookTitle = URLDecoder.decode(title, "UTF-8"),
                bookCoverUrl = URLDecoder.decode(cover, "UTF-8"),
                bookDescription = URLDecoder.decode(desc, "UTF-8"),
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("reading_goals") {
            ReadingGoalsScreen(
                goalDao = goalDao,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}