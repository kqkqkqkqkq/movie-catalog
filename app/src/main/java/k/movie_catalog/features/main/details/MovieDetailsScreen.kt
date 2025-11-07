package k.movie_catalog.features.main.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

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
                currentState.error != null -> MovieDetailsError(currentState.error)
                currentState.movie != null -> MovieDetailsContent(currentState.movie, onNavigateBack)
                else -> MovieDetailsError("Unexpected state")
            }
        }
    }
}
