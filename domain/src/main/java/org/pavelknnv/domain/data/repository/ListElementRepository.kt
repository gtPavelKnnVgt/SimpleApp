package org.pavelknnv.domain.data.repository

import org.pavelknnv.domain.data.entity.ListElement

interface ListElementRepository {
    suspend fun getElementList(): List<ListElement>

    suspend fun getElementListById(id: Long): ListElement

}