package dev.whysoezzy.domain.usecase

import dev.whysoezzy.domain.entity.ListElement
import dev.whysoezzy.domain.repository.ListElementRepository
import kotlinx.coroutines.delay

class ListElementUseCase(
    private val repository: ListElementRepository
):UseCase<Unit,List<ListElement>> {
    override suspend fun execute(data: Unit): List<ListElement> {
        delay(1000)
        return repository.getElementList()
    }
}