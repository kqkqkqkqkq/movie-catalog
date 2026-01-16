package k.moviecatalog.features.collections.edit

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

class EditCollectionsViewModel(
    private val collectionsRepository: CollectionsRepository = App.instance.collectionsRepository,
    private val dispatcherProvider: DispatcherProvider = App.instance.dispatcherProvider,
) : ViewModel() {

    private val _collectionsState = MutableStateFlow(EditCollectionsUiState())
    val collectionsState = _collectionsState.asStateFlow()

    fun updateCollection(collection: Collection) {
        _collectionsState.update { it.copy(collection = collection) }
    }

    fun editCollection(collectionName: String, onCollectionUpdate: () -> Unit) {
        viewModelScope.launch(dispatcherProvider.io) {
            try {
                _collectionsState.update { it.copy(isLoading = true) }
                val collection = _collectionsState.value.collection
                println(collection)
                collectionsRepository.updateCollection(collectionName, collection)
                _collectionsState.update { it.copy(isLoading = false) }
                withContext(dispatcherProvider.main) {
                    onCollectionUpdate()
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

    fun deleteCollection(collectionName: String, onDeleteCollection: () -> Unit) {
        viewModelScope.launch(dispatcherProvider.io) {
            try {
                _collectionsState.update { it.copy(isLoading = true) }
                val collection = _collectionsState.value.collection
                collectionsRepository.removeCollection(
                    collection.copy(
                        title = collectionName
                    )
                )
                _collectionsState.update { it.copy(isLoading = false) }
                withContext(dispatcherProvider.main) {
                    onDeleteCollection()
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