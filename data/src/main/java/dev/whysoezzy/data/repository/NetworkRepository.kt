package dev.whysoezzy.data.repository

import dev.whysoezzy.data.network.Api
import dev.whysoezzy.domain.entity.ListElement
import dev.whysoezzy.domain.repository.ListElementRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NetworkRepository(
    private val api: Api
): ListElementRepository {
    override suspend fun getElementList(): List<ListElement> = withContext(Dispatchers.IO) {
        api.getData().data.elements
    }

}