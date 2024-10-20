package dev.whysoezzy.data.network.response

import dev.whysoezzy.domain.data.entity.ListElement

data class ElementsResponse(
    val data: ElementsData
)

data class ElementsData(
    val elements:List<ListElement>
)
