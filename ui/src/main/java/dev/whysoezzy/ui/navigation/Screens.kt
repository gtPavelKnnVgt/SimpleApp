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
//enum class Screen {
//    MAIN,
//    DETAIL,
//}
//sealed class NavigationItem(val route: String) {
//    object Home : NavigationItem(Screen.MAIN.name)
//    object Login : NavigationItem(Screen.DETAIL.name)
//}