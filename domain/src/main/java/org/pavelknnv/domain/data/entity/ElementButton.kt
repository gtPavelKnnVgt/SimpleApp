package org.pavelknnv.domain.data.entity

data class ElementButton(
    val title: String,
    val id: ButtonType = ButtonType.DEFAULT
)

enum class ButtonType{
    DEFAULT
}