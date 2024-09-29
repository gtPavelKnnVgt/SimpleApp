package dev.whysoezzy.ui.main.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.whysoezzy.domain.usecase.ListElementUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val useCase: ListElementUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<MainState>(MainState.Loading)
    val state: StateFlow<MainState>
        get() = _state
    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        viewModelScope.launch {
            _state.emit(MainState.Error(throwable.message ?: "Ошибка"))
        }
    }

    init {
        viewModelScope.launch(context = exceptionHandler) {
            val result = useCase.execute(Unit)
            _state.emit(MainState.Content(result))
        }
    }
}