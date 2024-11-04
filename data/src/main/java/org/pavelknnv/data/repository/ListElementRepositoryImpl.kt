package org.pavelknnv.data.repository

import org.pavelknnv.domain.data.entity.ElementButton
import org.pavelknnv.domain.data.entity.ListElement
import org.pavelknnv.domain.data.repository.ListElementRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ListElementRepositoryImpl : ListElementRepository {
    override suspend fun getElementList(): List<ListElement> = withContext(Dispatchers.IO) {
        return@withContext listOf(
            ListElement(
                id = 0,
                title = "Игра в баскетбол",
                date = "04.12.2024",
                country = "Москва",
                image = "https://i.pinimg.com/736x/30/4c/66/304c662137511b54d254eebd98154d14.jpg",
                button = ElementButton(
                    title = "Товарищеская игра"
                )
            ),
            ListElement(
                id = 1,
                title = "Посещения врача",
                date = "12.12.2024",
                country = "Москва",
                image = "https://www.kostomuksha-city.ru/images/novostnaya_stroka/novosti/2018/oktyabr/Doctor-wagging-finger.jpg",
                button = ElementButton(
                    title = "Посещение терапевта"
                )
            ),
            ListElement(
                id = 2,
                title = "Учеба",
                date = "13.12.2024",
                country = "Москва",
                image = "https://sh2-kansk-r04.gosweb.gosuslugi.ru/netcat_files/45/237/2286871.jpg",
                button = ElementButton(
                    title = "Сдача зачетов"
                )
            ),
            ListElement(
                id = 3,
                title = "Работа",
                date = "20.04.2024",
                country = "Москва",
                image = "https://adler.mvk-sochi.ru/upload/resize_cache/webp/iblock/a27/2elxlm3qwdla7v5f895vf63ug8vmics8.webp",
                button = ElementButton(
                    title = "Выход из отпуска"
                )
            ),
        )
    }

    override suspend fun getElementListById(id: Long): ListElement {
        return ListElement(
            id = id,
            title = "Игра в баскетбол",
            date = "04.12.2024",
            country = "Москва",
            image = "https://i.pinimg.com/736x/30/4c/66/304c662137511b54d254eebd98154d14.jpg",
            button = ElementButton(
                title = "Товарищеская игра"
            )
        )
    }
}