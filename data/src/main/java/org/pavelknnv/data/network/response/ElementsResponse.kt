package org.pavelknnv.data.network.response

import org.pavelknnv.domain.data.entity.ListElement

data class ElementsResponse(
    val data: ElementsData
)

data class ElementsData(
    val elements:List<ListElement>
)
