package k.movie_catalog.features.collections.details

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetailsCollectionsViewModel : ViewModel() {

    private val _collectionsState = MutableStateFlow(DetailsCollectionsUiState())
    val collectionsState = _collectionsState.asStateFlow()
}