package k.moviecatalog.features.collections.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import k.moviecatalog.App
import k.moviecatalog.repositories.collections.CollectionsRepository
import k.moviecatalog.repositories.models.Collection
import k.moviecatalog.common.dispatcher.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateCollectionsViewModel(
    private val collectionsRepository: CollectionsRepository = App.instance.collectionsRepository,
    private val dispatcherProvider: DispatcherProvider = App.instance.dispatcherProvider,
) : ViewModel() {

    private val _collectionsState = MutableStateFlow(CreateCollectionsUiState())
    val collectionsState = _collectionsState.asStateFlow()

    fun updateCollection(collection: Collection) {
        _collectionsState.update { it.copy(collection = collection) }
    }

    fun createCollection(onCreateCollection: () -> Unit) {
        viewModelScope.launch(dispatcherProvider.io) {
            _collectionsState.update { it.copy(isLoading = true) }
            try {
                val collection = _collectionsState.value.collection
                require(collection.title.isNotBlank()) { "Title cannot be empty" }
                collectionsRepository.createCollection(collection)
                withContext(dispatcherProvider.main) {
                    onCreateCollection()
                }
            } catch (e: Exception) {
                _collectionsState.update {
                    it.copy(error = e.message)
                }
            } finally {
                _collectionsState.update { it.copy(isLoading = false) }
            }
        }
    }
}