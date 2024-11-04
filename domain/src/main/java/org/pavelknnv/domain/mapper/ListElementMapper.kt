package org.pavelknnv.domain.mapper

import org.pavelknnv.domain.data.entity.ListElement
import org.pavelknnv.domain.data.repository.LocalStorageRepository
import org.pavelknnv.domain.entity.ListElementEntity

class ListElementMapper(
    private val localStorageRepository: LocalStorageRepository
) : Mapper<ListElement, ListElementEntity> {
    override fun map(from: ListElement): ListElementEntity {
        return ListElementEntity(
            id = from.id,
            image = from.image,
            title = from.title,
            date = from.date,
            country = from.country,
            button = from.button,
            like = localStorageRepository.isLiked(from.id)
        )
    }
}