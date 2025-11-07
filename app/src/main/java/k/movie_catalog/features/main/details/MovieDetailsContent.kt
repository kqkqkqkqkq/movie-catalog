package k.movie_catalog.features.main.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import k.movie_catalog.R
import k.movie_catalog.features.main.details.components.CollapsedTopBar
import k.movie_catalog.features.main.details.components.ExpandedTopBar
import k.movie_catalog.features.main.details.components.ReviewComponent
import k.movie_catalog.repositories.models.MovieDetails

val COLLAPSED_TOP_BAR_HEIGHT = 56.dp
val EXPANDED_TOP_BAR_HEIGHT = 256.dp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MovieDetailsContent(
    movie: MovieDetails,
    onNavigateBack: () -> Unit,
) {
    val listState = rememberLazyListState()
    val isCollapsed: Boolean by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 }
    }

    val properties = mapOf(
        stringResource(R.string.year) to movie.year.toString(),
        stringResource(R.string.country) to movie.country,
        stringResource(R.string.time) to "$${movie.time} m.",
        stringResource(R.string.slogan) to movie.tagline,
        stringResource(R.string.director) to movie.director,
        stringResource(R.string.budget) to "$${movie.budget}",
        stringResource(R.string.world) to "$${movie.fees}",
        stringResource(R.string.age_limit) to "$${movie.ageLimit}+",
    )

    Scaffold(
        topBar = {
            AnimatedVisibility(
                visible = isCollapsed,
                enter = fadeIn(
                    animationSpec = tween(durationMillis = 300)
                ) + slideInVertically(),
                exit = fadeOut(
                    animationSpec = tween(durationMillis = 300)
                ) + slideOutVertically()
            ) {
                CollapsedTopBar(
                    name = movie.name ?: stringResource(R.string.unknown_movie),
                    onBackClick = onNavigateBack,
                    onFavouriteClick = {},
                )
            }
        },
        modifier = Modifier
            .padding(top = 32.dp),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            item {
                ExpandedTopBar(
                    name = movie.name ?: stringResource(R.string.unknown_movie),
                    imageUrl = movie.poster ?: "",
                    onBackClick = onNavigateBack,
                    onFavouriteClick = {},
                )
            }
            item {
                Text(
                    text = movie.description ?: "No description",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                )
            }
            item {
                Button(
                    onClick = {
                        TODO("show list")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                ) {
                    Text(
                        text = stringResource(R.string.add_to_favourites),
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier
                    )
                }
            }
            item {
                Text(
                    text = stringResource(R.string.about_movie),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                )
            }
            item {
                properties.forEach { (name, property) ->
                    if (!property.isNullOrBlank()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = 4.dp,
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(0.3f),
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                                horizontalAlignment = Alignment.Start,
                            ) {
                                Text(
                                    text = name,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onBackground.copy(0.5f),
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                                horizontalAlignment = Alignment.Start,
                            ) {
                                Text(
                                    text = property,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onBackground,
                                )
                            }
                        }
                    }
                }
            }
            item {
                Text(
                    text = stringResource(R.string.genres),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                )
            }
            item {
                FlowRow(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    movie.genres.forEach { genre ->
                        if (genre.name != null) {
                            Box(
                                modifier = Modifier
                                    .clip(MaterialTheme.shapes.medium)
                                    .background(MaterialTheme.colorScheme.primary),
                            ) {
                                Text(
                                    text = genre.name,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp, vertical = 4.dp),
                                )
                            }
                        }
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = stringResource(R.string.reviews),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier,
                    )
                    IconButton(
                        onClick = {
                            TODO("Add review")
                        },
                        modifier = Modifier
                            .size(24.dp),
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.icon_add),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.fillMaxSize(),
                        )
                    }
                }
            }
            items(movie.reviews) { review ->
                ReviewComponent(review)
            }
        }
    }
}
