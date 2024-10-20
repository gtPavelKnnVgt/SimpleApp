package dev.whysoezzy.domain.entity

import dev.whysoezzy.domain.data.entity.ElementButton

data class ListElementEntity(
    val id: Long,
    val title: String,
    val date: String,
    val country: String,
    val image:String,
    val button: ElementButton?,
    val like: Boolean
)
