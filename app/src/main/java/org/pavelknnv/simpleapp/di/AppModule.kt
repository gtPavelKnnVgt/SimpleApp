package org.pavelknnv.simpleapp.di

import android.content.Context
import org.pavelknnv.data.network.Api
import org.pavelknnv.data.repository.CacheRepositoryImpl
import org.pavelknnv.data.repository.ListElementRepositoryImpl
import org.pavelknnv.data.repository.LocalStorageRepositoryImpl
import org.pavelknnv.domain.data.entity.ListElement
import org.pavelknnv.domain.data.repository.CacheRepository
import org.pavelknnv.domain.data.repository.ListElementRepository
import org.pavelknnv.domain.data.repository.LocalStorageRepository
import org.pavelknnv.domain.entity.ListElementEntity
import org.pavelknnv.domain.mapper.ListElementMapper
import org.pavelknnv.domain.mapper.Mapper
import org.pavelknnv.domain.usecase.FindElementsByIdUseCase
import org.pavelknnv.domain.usecase.FindListElementsUseCase
import org.pavelknnv.ui.details.vm.DetailsViewModel
import org.pavelknnv.ui.main.vm.MainViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://mocki.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }
    single {
        get<Context>().getSharedPreferences("prefs", Context.MODE_PRIVATE)
    }
    single<LocalStorageRepository> { LocalStorageRepositoryImpl(get()) }
    single<CacheRepository> { CacheRepositoryImpl() }
    single<ListElementRepository> { ListElementRepositoryImpl() }
    single { FindListElementsUseCase(get(), get()) }
    single { FindElementsByIdUseCase(get(), get(), get()) }
    single<Mapper<ListElement, ListElementEntity>> { ListElementMapper(get()) }
    viewModel { MainViewModel(get(), get(),get()) }
    viewModel { DetailsViewModel(get(), get(), get()) }

}