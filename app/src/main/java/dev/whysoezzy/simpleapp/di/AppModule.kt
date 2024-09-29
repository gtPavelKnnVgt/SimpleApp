package dev.whysoezzy.simpleapp.di

import dev.whysoezzy.data.network.Api
import dev.whysoezzy.data.repository.ListElementRepositoryImpl
import dev.whysoezzy.data.repository.NetworkRepository
import dev.whysoezzy.domain.repository.ListElementRepository
import dev.whysoezzy.domain.usecase.ListElementUseCase
import dev.whysoezzy.ui.main.vm.MainViewModel
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
    single<ListElementRepository> { NetworkRepository(get()) }
    single { ListElementUseCase(get()) }
    viewModel { MainViewModel(get()) }

}