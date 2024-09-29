package dev.whysoezzy.data.repository

import dev.whysoezzy.domain.entity.ElementButton
import dev.whysoezzy.domain.entity.ListElement
import dev.whysoezzy.domain.repository.ListElementRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ListElementRepositoryImpl: ListElementRepository {
    override suspend fun getElementList(): List<ListElement> = withContext(Dispatchers.IO){
        return@withContext listOf(
            ListElement(
                title = "Heading",
                subtitle = "Subtitle",
                image = "https://steamuserimages-a.akamaihd.net/ugc/2264809616946063909/27B3C5875557E755999D2C424162F954F2ECF23C/?imw=512&&ima=fit&impolicy=Letterbox&imcolor=%23000000&letterbox=false",
                button = ElementButton(
                    title = "Download"
                )
            ),
            ListElement(
                title = "Heading",
                subtitle = "Subtitle",
                image = "https://steamuserimages-a.akamaihd.net/ugc/2264809616946063909/27B3C5875557E755999D2C424162F954F2ECF23C/?imw=512&&ima=fit&impolicy=Letterbox&imcolor=%23000000&letterbox=false",
                button = ElementButton(
                    title = "Download"
                )
            ),
            ListElement(
                title = "Heading",
                subtitle = "Subtitle",
                image = "https://steamuserimages-a.akamaihd.net/ugc/2264809616946063909/27B3C5875557E755999D2C424162F954F2ECF23C/?imw=512&&ima=fit&impolicy=Letterbox&imcolor=%23000000&letterbox=false",
                button = ElementButton(
                    title = "Download"
                )
            ),
            ListElement(
                title = "Heading",
                subtitle = "Subtitle",
                image = "https://steamuserimages-a.akamaihd.net/ugc/2264809616946063909/27B3C5875557E755999D2C424162F954F2ECF23C/?imw=512&&ima=fit&impolicy=Letterbox&imcolor=%23000000&letterbox=false",
                button = ElementButton(
                    title = "Download"
                )
            ),
        )
    }
}