package dev.whysoezzy.domain.entity

data class ElementButton(
    val title: String,
    val id: ButtonType = ButtonType.DEFAULT
)

enum class ButtonType{
    DEFAULT,
}
