package k.movie_catalog.features.collections.edit

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class EditCollectionsViewModel(

) : ViewModel() {

    private val _collectionsState = MutableStateFlow(EditCollectionsUiState())
    val collectionsState = _collectionsState.asStateFlow()

    init {

    }
}