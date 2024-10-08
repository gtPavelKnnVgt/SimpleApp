package dev.whysoezzy.ui.navigation

enum class Screens(
    val route: String,
    val label: String
) {
    MAIN(
        route = "main",
        label = "MainScreen"
    ),
    DETAIL(
        route = "detail",
        "DetailScreen"
    )
}