package dev.whysoezzy.domain.usecase

import dev.whysoezzy.domain.data.entity.ListElement
import dev.whysoezzy.domain.data.repository.ListElementRepository
import dev.whysoezzy.domain.entity.ListElementEntity
import dev.whysoezzy.domain.mapper.Mapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class ListElementUseCase(
    private val repository: ListElementRepository,
    private val elementMapper: Mapper<ListElement, ListElementEntity>
) : UseCase<Unit, List<ListElementEntity>> {
    override suspend fun execute(data: Unit): List<ListElementEntity> =
        withContext(Dispatchers.Default) {
            delay(500)
            return@withContext repository.getElementList().map {
                elementMapper.map(it)
            }
        }
}