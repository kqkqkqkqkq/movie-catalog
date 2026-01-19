package k.moviecatalog.features.main.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import k.moviecatalog.features.main.details.state.MovieDetailsContent
import k.moviecatalog.features.main.details.state.MovieDetailsError
import k.moviecatalog.features.main.details.state.MovieDetailsLoading

@Composable
fun MovieDetailsScreen(
    viewModel: MovieDetailsViewModel,
    onNavigateBack: () -> Unit,
) {
    val state by viewModel.movieDetailState.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            val currentState = state
            when {
                currentState.isLoading -> MovieDetailsLoading()
                currentState.error != null -> MovieDetailsError(currentState.error) {
                    viewModel.back()
                }
                currentState.movie != null -> MovieDetailsContent(
                    state = currentState,
                    onNavigateBack = onNavigateBack,
                    onCollectionAdd = { viewModel.addToCollection(it) },
                    onFavourite = { viewModel.onFavourite() },
                    onCreateReview = { viewModel.createReview(it) },
                    onUpdateReview = { viewModel.updateReview(it) },
                    onDeleteReview = { viewModel.deleteReview(it) },
                )

                else -> MovieDetailsLoading()
            }
        }
    }
}
