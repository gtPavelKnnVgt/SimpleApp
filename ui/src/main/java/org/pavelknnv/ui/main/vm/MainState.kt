package org.pavelknnv.ui.main.vm

import org.pavelknnv.domain.entity.ListElementEntity

sealed class MainState {
    data class Content(
        val list: List<ListElementEntity>
    ) : MainState()

    data class Error(
        val message: String
    ) : MainState()

    object Loading : MainState()
}