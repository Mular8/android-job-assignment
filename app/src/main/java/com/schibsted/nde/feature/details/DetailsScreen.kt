package com.schibsted.nde.feature.details

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.schibsted.nde.feature.meals.MealsViewModel
import com.schibsted.nde.model.MealResponse

@Composable
fun DetailsScreen(idMeal: String, viewModel: MealsViewModel) {
    val uiState = viewModel.state.collectAsState()
    val mealResponse = uiState.value.meals.firstOrNull { it.idMeal == idMeal }
    if (mealResponse == null) {
        return
    }
    DetailsContent(mealResponse)
}

@Composable
private fun DetailsContent(mealResponse: MealResponse) {
    Text(text = mealResponse.strMeal)
}