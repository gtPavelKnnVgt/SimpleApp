package dev.whysoezzy.domain.repository

import dev.whysoezzy.domain.entity.ListElement

interface ListElementRepository {
    suspend fun getElementList():List<ListElement>

}