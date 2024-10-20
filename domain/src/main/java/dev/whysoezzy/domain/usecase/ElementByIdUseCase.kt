package dev.whysoezzy.domain.usecase

import android.content.res.Resources.NotFoundException
import dev.whysoezzy.domain.entity.ListElement
import dev.whysoezzy.domain.repository.CacheRepository
import dev.whysoezzy.domain.repository.ListElementRepository

class ElementByIdUseCase(
    private val repository: ListElementRepository,
    private val cacheRepository: CacheRepository
): UseCase<Long,ListElement> {
    override suspend fun execute(data: Long): ListElement {
        val cached = cacheRepository.getCache<List<ListElement>>("getList")
        if(cached != null){
            return cached.find { it.id == data } ?: throw NotFoundException()
        }
        return repository.getElementListById(data)
    }
}