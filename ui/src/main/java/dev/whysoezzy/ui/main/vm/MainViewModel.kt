package dev.whysoezzy.ui.main.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dev.whysoezzy.domain.data.repository.LocalStorageRepository
import dev.whysoezzy.domain.entity.ListElementEntity
import dev.whysoezzy.domain.usecase.ListElementUseCase
import dev.whysoezzy.ui.main.MainScreenRoute
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel(
    private val useCase: ListElementUseCase,
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

    fun like(elementEntity: ListElementEntity, like: Boolean) {
        localStorageRepository.like(elementEntity.id, like)
        val currentState = _state.value
        if (currentState is MainState.Content) {
            val updatedList = currentState.list.map { element ->
                if (element.id == elementEntity.id) {
                    element.copy(like = like)
                } else {
                    element
                }
            }
            _state.value = MainState.Content(updatedList)
        }
    }


}