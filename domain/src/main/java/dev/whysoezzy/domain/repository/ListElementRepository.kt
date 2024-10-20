package dev.whysoezzy.domain.repository

import dev.whysoezzy.domain.entity.DetailElement
import dev.whysoezzy.domain.entity.ListElement

interface ListElementRepository {
    suspend fun getElementList():List<ListElement>

    suspend fun getElementListById(id:Long): ListElement

}