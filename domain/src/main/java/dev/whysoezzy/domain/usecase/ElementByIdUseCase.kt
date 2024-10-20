package dev.whysoezzy.domain.usecase

import android.content.res.Resources.NotFoundException
import dev.whysoezzy.domain.data.entity.ListElement
import dev.whysoezzy.domain.data.repository.CacheRepository
import dev.whysoezzy.domain.data.repository.ListElementRepository
import dev.whysoezzy.domain.entity.ListElementEntity
import dev.whysoezzy.domain.mapper.Mapper

class ElementByIdUseCase(
    private val repository: ListElementRepository,
    private val cacheRepository: CacheRepository,
    private val elementMapper: Mapper<ListElement, ListElementEntity>
) : UseCase<Long, ListElementEntity> {
    override suspend fun execute(data: Long): ListElementEntity {
        val cached = cacheRepository.getCache<List<ListElement>>("getList")
        if (cached != null) {
            return cached.find { it.id == data }?.let {
                elementMapper.map(it)
            } ?: throw NotFoundException()
        }
        return elementMapper.map(repository.getElementListById(data))
    }
}