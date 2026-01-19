package k.moviecatalog.features.main.details.state

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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import k.moviecatalog.R
import k.moviecatalog.common.extensions.formatMoney
import k.moviecatalog.features.main.details.MovieDetailsUiState
import k.moviecatalog.features.main.details.components.CollapsedTopBar
import k.moviecatalog.features.main.details.components.CollectionMenuItem
import k.moviecatalog.features.main.details.components.ExpandedTopBar
import k.moviecatalog.features.main.details.components.GenresSection
import k.moviecatalog.features.main.details.components.PropertiesSection
import k.moviecatalog.features.main.details.components.ReviewComponent
import k.moviecatalog.features.main.details.components.ReviewDialog
import k.moviecatalog.repositories.models.Collection
import k.moviecatalog.repositories.models.Review

val STATUS_BAR_HEIGHT = 32.dp
val COLLAPSED_TOP_BAR_HEIGHT = 56.dp + STATUS_BAR_HEIGHT
val EXPANDED_TOP_BAR_HEIGHT = 256.dp + STATUS_BAR_HEIGHT

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MovieDetailsContent(
    state: MovieDetailsUiState,
    onNavigateBack: () -> Unit,
    onCollectionAdd: (Collection) -> Unit,
    onFavourite: () -> Unit,
    onCreateReview: (Review) -> Unit,
    onUpdateReview: (Review) -> Unit,
    onDeleteReview: (Review) -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    val isCollapsed by remember { derivedStateOf { listState.firstVisibleItemIndex > 0 } }
    var expanded by remember { mutableStateOf(false) }
    val collections = state.availableCollections ?: emptyList()
    val favouritesIcon =
        if (state.inFavourites) R.drawable.icon_heart_filled else R.drawable.icon_heart

    val properties = mapOf(
        stringResource(R.string.year) to state.movie?.year.toString(),
        stringResource(R.string.country) to state.movie?.country,
        stringResource(R.string.time) to "${state.movie?.time} ${stringResource(R.string.time_unit)}",
        stringResource(R.string.slogan) to state.movie?.tagline,
        stringResource(R.string.director) to state.movie?.director,
        stringResource(R.string.budget) to "$${state.movie?.budget.formatMoney()}",
        stringResource(R.string.world) to "$${state.movie?.fees.formatMoney()}",
        stringResource(R.string.age_limit) to "${state.movie?.ageLimit}+",
    )

    Scaffold(
        topBar = {
            AnimatedVisibility(
                visible = isCollapsed,
                enter = fadeIn(animationSpec = tween(durationMillis = 300)) + slideInVertically(),
                exit = fadeOut(animationSpec = tween(durationMillis = 300)) + slideOutVertically(),
            ) {
                CollapsedTopBar(
                    icon = favouritesIcon,
                    name = state.movie?.name ?: stringResource(R.string.unknown_movie),
                    onBackClick = onNavigateBack,
                    onFavouriteClick = onFavourite,
                )
            }
        },
//        modifier = Modifier.padding(top = 32.dp),
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
                    icon = favouritesIcon,
                    name = state.movie?.name ?: stringResource(R.string.unknown_movie),
                    imageUrl = state.movie?.poster.orEmpty(),
                    onBackClick = onNavigateBack,
                    onFavouriteClick = onFavourite,
                )
            }
            item {
                Text(
                    text = state.movie?.description.orEmpty(),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
            }
            item {
                Box {
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxWidth()
                    ) {
                        if (collections.isEmpty()) {
                            CollectionMenuItem(
                                title = stringResource(R.string.empty_collections),
                                onClick = { expanded = false },
                            )
                        } else {
                            collections.forEach { collection ->
                                CollectionMenuItem(
                                    title = collection.title,
                                    onClick = {
                                        expanded = false
                                        onCollectionAdd(collection)
                                    },
                                    trailingIcon = null, // TODO("Icon shows if movie in collection")
                                )
                            }
                        }
                    }
                    Button(
                        onClick = { expanded = true },
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
            }
            item {
                Text(
                    text = stringResource(R.string.about_movie),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
                Spacer(modifier = Modifier.height(8.dp))
                properties.forEach { (name, property) ->
                    if (!property.isNullOrBlank()) {
                        PropertiesSection(name, property)
                    }
                }
            }
            item {
                Text(
                    text = stringResource(R.string.genres),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
                Spacer(modifier = Modifier.height(8.dp))
                GenresSection(state.movie?.genres ?: emptyList())
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
                            showDialog = true
                        },
                        modifier = Modifier.size(24.dp),
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
            items(state.movie?.reviews ?: emptyList()) { review ->
                ReviewComponent(
                    review = review,
                    currentUserProfile = requireNotNull(state.currentUserProfile),
                    onUpdateReview = onUpdateReview,
                    onDeleteReview = onDeleteReview,
                )
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        if (showDialog) {
            ReviewDialog(
                currentUserProfile = state.currentUserProfile,
                onSaveReview = onCreateReview,
                onDismissRequest = { showDialog = false }
            )
        }
    }
}
