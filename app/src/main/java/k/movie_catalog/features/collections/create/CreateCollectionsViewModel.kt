package k.movie_catalog.features.collections.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import k.movie_catalog.repositories.collections.CollectionsRepository
import k.movie_catalog.repositories.models.Collection
import k.movie_catalog.utils.dispatcher.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateCollectionsViewModel(
    private val collectionsRepository: CollectionsRepository,
    private val dispatcherProvider: DispatcherProvider,
) : ViewModel() {

    private val _collectionsState = MutableStateFlow(CreateCollectionsUiState())
    val collectionsState = _collectionsState.asStateFlow()

    fun updateCollection(collection: Collection) {
        _collectionsState.update { it.copy(collection = collection) }
    }


    fun createCollection(onCreateCollection: () -> Unit) {
        viewModelScope.launch(dispatcherProvider.io) {
            try {
                _collectionsState.update { it.copy(isLoading = true) }
                val collection = _collectionsState.value.collection
                if (collection.title.isBlank()) {
                    throw IllegalArgumentException("Title cannot be empty")
                }
                collectionsRepository.createCollection(collection)
                _collectionsState.update { it.copy(isLoading = false) }
                withContext(dispatcherProvider.main) {
                    onCreateCollection()
                }
            } catch (e: Exception) {
                _collectionsState.update {
                    it.copy(
                        error = e.message,
                        isLoading = false,
                    )
                }
            }
        }
    }
}