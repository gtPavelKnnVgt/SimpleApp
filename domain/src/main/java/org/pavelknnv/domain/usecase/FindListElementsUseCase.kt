package org.pavelknnv.domain.usecase

import org.pavelknnv.domain.data.entity.ListElement
import org.pavelknnv.domain.data.repository.ListElementRepository
import org.pavelknnv.domain.entity.ListElementEntity
import org.pavelknnv.domain.mapper.Mapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FindListElementsUseCase(
    private val repository: ListElementRepository,
    private val elementMapper: Mapper<ListElement, ListElementEntity>
) : UseCase<Unit, List<ListElementEntity>> {
    override suspend fun execute(data: Unit): List<ListElementEntity> =
        withContext(Dispatchers.Default) {
            return@withContext repository.getElementList().map {
                elementMapper.map(it)
            }
        }
}