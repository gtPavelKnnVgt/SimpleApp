package dev.whysoezzy.domain.mapper

import dev.whysoezzy.domain.data.entity.ListElement
import dev.whysoezzy.domain.data.repository.LocalStorageRepository
import dev.whysoezzy.domain.entity.ListElementEntity

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