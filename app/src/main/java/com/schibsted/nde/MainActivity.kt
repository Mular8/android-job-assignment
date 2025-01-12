package com.schibsted.nde

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.schibsted.nde.feature.details.DetailsScreen
import com.schibsted.nde.feature.meals.MealsScreen
import com.schibsted.nde.ui.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            AppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    NavGraph(navController)
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "meals") {
        composable("meals") { MealsScreen(hiltViewModel(), navController) }
        composable(
            route = "details/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            if (id != null) {
                DetailsScreen(idMeal = id, hiltViewModel())
            }
        }
    }
}