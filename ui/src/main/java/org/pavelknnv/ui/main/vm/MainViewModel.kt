package org.pavelknnv.ui.main.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import org.pavelknnv.domain.data.repository.LocalStorageRepository
import org.pavelknnv.domain.entity.ListElementEntity
import org.pavelknnv.domain.usecase.FindListElementsUseCase
import org.pavelknnv.ui.main.MainScreenRoute
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel(
    private val useCase: FindListElementsUseCase,
    private val localStorageRepository: LocalStorageRepository,
    private val handle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow<MainState>(MainState.Loading)
    val state: StateFlow<MainState>
        get() = _state

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            _state.emit(MainState.Error(throwable.message ?: "Ошибка"))
        }
    }

    init {
        loadContent()
        Timber.e(handle.toRoute<MainScreenRoute>().toString())
    }

    private fun loadContent() {
        viewModelScope.launch(context = exceptionHandler) {
            val result = useCase.execute(Unit).map { element ->
                element.copy(like = localStorageRepository.isLiked(element.id))
            }
            _state.emit(MainState.Content(result))
        }
    }

    fun refreshContent(){
        loadContent()
    }

    fun like(elementEntity: ListElementEntity, like: Boolean) {
        localStorageRepository.like(elementEntity.id, like)
        loadContent()
    }
}