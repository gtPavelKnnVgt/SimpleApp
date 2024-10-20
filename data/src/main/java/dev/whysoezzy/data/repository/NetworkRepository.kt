package dev.whysoezzy.data.repository

import android.content.res.Resources
import dev.whysoezzy.data.network.Api
import dev.whysoezzy.domain.data.entity.ListElement
import dev.whysoezzy.domain.data.repository.CacheRepository
import dev.whysoezzy.domain.data.repository.ListElementRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NetworkRepository(
    private val api: Api,
    private val cacheRepository: CacheRepository
) : ListElementRepository {
    override suspend fun getElementList(): List<ListElement> = withContext(Dispatchers.IO) {
        cacheRepository.getAndSave(
            force = true,
            key = "getList",
            remote = {
                api.getData().data.elements
            }
        )
    }

    override suspend fun getElementListById(id: Long): ListElement = withContext(Dispatchers.IO) {
        cacheRepository.getAndSave(
            force = true,
            key = "getList",
            remote = {
                api.getData().data.elements
            }
        ).find { it.id == id } ?: throw Resources.NotFoundException()
    }
}