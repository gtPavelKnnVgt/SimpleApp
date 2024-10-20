package dev.whysoezzy.ui.details.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dev.whysoezzy.domain.data.repository.LocalStorageRepository
import dev.whysoezzy.domain.entity.ListElementEntity
import dev.whysoezzy.domain.usecase.ElementByIdUseCase
import dev.whysoezzy.ui.details.DetailsScreenRoute
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class DetailsViewModel(
    private val useCase: ElementByIdUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val storage: LocalStorageRepository
) : ViewModel() {
    private val _state = MutableStateFlow<DetailsState>(DetailsState.Loading)
    val state: StateFlow<DetailsState>
        get() = _state

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        viewModelScope.launch {
            val title = when (val curState = _state.value) {
                is DetailsState.Content -> curState.title
                is DetailsState.Error -> curState.title
                is DetailsState.Loading -> "Loading"
            }
            _state.emit(DetailsState.Error(title, throwable.message ?: "Ошибочка"))
        }
    }

    init {
        Timber.e(savedStateHandle.toRoute<DetailsScreenRoute>().toString())
        loadContent()
    }

    fun markAsRead() {
        val route = savedStateHandle.toRoute<DetailsScreenRoute>()
        storage.markAsRead(route.id)
        loadContent()
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
    }

}