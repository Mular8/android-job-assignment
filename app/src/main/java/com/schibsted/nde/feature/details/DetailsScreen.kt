package com.schibsted.nde.feature.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
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
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 20.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(mealResponse.strMealThumb)
                .crossfade(true)
                .build(),
            contentDescription = mealResponse.strMeal
        )
        Text(
            text = mealResponse.strMeal,
            fontSize = 32.sp,
            modifier = Modifier.padding(top = 16.dp)
        )
        mealResponse.strYoutube?.let {
            YouTubePlayerComposable(
                videoId = it.extractId(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(top = 16.dp)
            )
        }
    }
}

private fun String.extractId(): String = this.substringAfter("https://www.youtube.com/watch?v=")

@Composable
fun YouTubePlayerComposable(videoId: String, modifier: Modifier = Modifier) {
    AndroidView(
        factory = { context ->
            YouTubePlayerView(context).apply {
                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.loadVideo(videoId, 0f)
                    }
                })
            }
        },
        modifier = modifier
    )
}