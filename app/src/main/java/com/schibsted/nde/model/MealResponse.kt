package com.schibsted.nde.model

import com.schibsted.nde.database.MealEntity

data class MealResponse(
    val idMeal: String,
    val strMeal: String,
    val strCategory: String,
    val strMealThumb: String,
    val strYoutube: String?,
) {
    constructor(mealEntity: MealEntity) : this(
        idMeal = mealEntity.id,
        strMeal = mealEntity.strMeal,
        strCategory = mealEntity.strCategory,
        strMealThumb = mealEntity.strMealThumb,
        strYoutube = mealEntity.strYoutube
    )

    fun toEntity() = MealEntity(
        id = idMeal,
        strMeal = strMeal,
        strCategory = strCategory,
        strMealThumb = strMealThumb,
        strYoutube = strYoutube
    )
}