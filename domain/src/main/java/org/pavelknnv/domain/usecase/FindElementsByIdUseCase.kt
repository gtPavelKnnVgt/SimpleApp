package org.pavelknnv.domain.usecase

import android.content.res.Resources.NotFoundException
import org.pavelknnv.domain.data.entity.ListElement
import org.pavelknnv.domain.data.repository.CacheRepository
import org.pavelknnv.domain.data.repository.ListElementRepository
import org.pavelknnv.domain.entity.ListElementEntity
import org.pavelknnv.domain.mapper.Mapper

class FindElementsByIdUseCase(
    private val repository: ListElementRepository,
    private val cacheRepository: CacheRepository,
    private val elementMapper: Mapper<ListElement, ListElementEntity>
) : UseCase<Long, ListElementEntity> {
    override suspend fun execute(data: Long): ListElementEntity {
        val optionalCached = cacheRepository.getCache<List<ListElement>>("getList")
        if (optionalCached != null) {
            return optionalCached.find { it.id == data }?.let {
                elementMapper.map(it)
            } ?: throw NotFoundException()
        }
        return elementMapper.map(repository.getElementListById(data))
    }
}