package dev.whysoezzy.domain.entity

data class ListElement(
    val id: Long,
    val title: String,
    val date: String,
    val country: String,
    val image:String,
    val button: ElementButton?
)
