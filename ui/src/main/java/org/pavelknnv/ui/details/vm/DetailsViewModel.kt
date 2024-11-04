package org.pavelknnv.ui.details.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import org.pavelknnv.domain.data.repository.LocalStorageRepository
import org.pavelknnv.domain.entity.ListElementEntity
import org.pavelknnv.domain.usecase.FindElementsByIdUseCase
import org.pavelknnv.ui.details.DetailsScreenRoute
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class DetailsViewModel(
    private val useCase: FindElementsByIdUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val storage: LocalStorageRepository
) : ViewModel() {
    private val _state = MutableStateFlow<DetailsState>(DetailsState.Loading)
    val state: StateFlow<DetailsState>
        get() = _state

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            val title = when (val curState = _state.value) {
                is DetailsState.Content -> curState.title
                is DetailsState.Error -> curState.title
                is DetailsState.Loading -> "Loading"
            }
            _state.emit(DetailsState.Error(title, throwable.message ?: "Ошибка"))
        }
    }

    init {
        Timber.e(savedStateHandle.toRoute<DetailsScreenRoute>().toString())
        loadContent()
    }

    fun markAsRead() {
        val route = savedStateHandle.toRoute<DetailsScreenRoute>()
        storage.markAsRead(route.id)
    }

    private fun loadContent() {
        viewModelScope.launch(context = exceptionHandler) {
            val route = savedStateHandle.toRoute<DetailsScreenRoute>()
            val result = useCase.execute(route.id)
            _state.emit(DetailsState.Content(result, storage.isMarkAsRead(route.id)))
        }
    }

    fun like(elementEntity: ListElementEntity, like: Boolean) {
        storage.like(elementEntity.id, like)
        loadContent()
    }

}