package com.schibsted.nde.data

import android.util.Log
import com.schibsted.nde.api.BackendApi
import com.schibsted.nde.database.MealEntityDao
import com.schibsted.nde.model.MealResponse
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "MealsRepository"

@Singleton
class MealsRepository @Inject constructor(
    private val backendApi: BackendApi,
    private val mealEntityDao: MealEntityDao
) {
    suspend fun getMeals(): List<MealResponse> {
        val listFromDb = mealEntityDao.getAll().firstOrNull()
        return if (listFromDb?.isNotEmpty() == true) {
            Log.i(TAG, "getMeals: from database")
            listFromDb.map { MealResponse(it) }
        } else {
            Log.i(TAG, "getMeals: from backend")
            backendApi.getMeals().meals.also { mealResponses ->
                mealEntityDao.insertMeals(
                    mealResponses.map { it.toEntity() })
            }
        }
    }
}