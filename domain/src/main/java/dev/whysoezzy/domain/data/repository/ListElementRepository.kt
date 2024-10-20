package dev.whysoezzy.domain.data.repository

import dev.whysoezzy.domain.data.entity.ListElement

interface ListElementRepository {
    suspend fun getElementList(): List<ListElement>

    suspend fun getElementListById(id: Long): ListElement

}