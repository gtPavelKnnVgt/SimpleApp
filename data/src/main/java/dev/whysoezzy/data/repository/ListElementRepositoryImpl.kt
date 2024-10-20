package dev.whysoezzy.data.repository

import dev.whysoezzy.domain.data.entity.ElementButton
import dev.whysoezzy.domain.data.entity.ListElement
import dev.whysoezzy.domain.data.repository.ListElementRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ListElementRepositoryImpl : ListElementRepository {
    override suspend fun getElementList(): List<ListElement> = withContext(Dispatchers.IO) {
        return@withContext listOf(
            ListElement(
                id = 0,
                title = "Meetup",
                date = "20.04.2024",
                country = "Moscow",
                image = "https://steamuserimages-a.akamaihd.net/ugc/2264809616946063909/27B3C5875557E755999D2C424162F954F2ECF23C/?imw=512&&ima=fit&impolicy=Letterbox&imcolor=%23000000&letterbox=false",
                button = ElementButton(
                    title = "Подробнее"
                )
            ),
            ListElement(
                id = 1,
                title = "Meetup",
                date = "20.04.2024",
                country = "Moscow",
                image = "https://steamuserimages-a.akamaihd.net/ugc/2264809616946063909/27B3C5875557E755999D2C424162F954F2ECF23C/?imw=512&&ima=fit&impolicy=Letterbox&imcolor=%23000000&letterbox=false",
                button = ElementButton(
                    title = "Подробнее"
                )
            ),
            ListElement(
                id = 2,
                title = "Meetup",
                date = "20.04.2024",
                country = "Moscow",
                image = "https://steamuserimages-a.akamaihd.net/ugc/2264809616946063909/27B3C5875557E755999D2C424162F954F2ECF23C/?imw=512&&ima=fit&impolicy=Letterbox&imcolor=%23000000&letterbox=false",
                button = ElementButton(
                    title = "Подробнее"
                )
            ),
            ListElement(
                id = 3,
                title = "Meetup",
                date = "20.04.2024",
                country = "Moscow",
                image = "https://steamuserimages-a.akamaihd.net/ugc/2264809616946063909/27B3C5875557E755999D2C424162F954F2ECF23C/?imw=512&&ima=fit&impolicy=Letterbox&imcolor=%23000000&letterbox=false",
                button = ElementButton(
                    title = "Подробнее"
                )
            ),
        )
    }

    override suspend fun getElementListById(id: Long): ListElement {
        return ListElement(
            id = 0,
            title = "Meetup",
            date = "20.04.2024",
            country = "Moscow",
            image = "https://steamuserimages-a.akamaihd.net/ugc/2264809616946063909/27B3C5875557E755999D2C424162F954F2ECF23C/?imw=512&&ima=fit&impolicy=Letterbox&imcolor=%23000000&letterbox=false",
            button = ElementButton(
                title = "Подробнее"
            )
        )
    }


}