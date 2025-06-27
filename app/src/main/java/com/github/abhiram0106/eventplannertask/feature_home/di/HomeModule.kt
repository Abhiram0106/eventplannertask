package com.github.abhiram0106.eventplannertask.feature_home.di

import com.github.abhiram0106.eventplannertask.App
import com.github.abhiram0106.eventplannertask.feature_home.data.HomeRepositoryImpl
import com.github.abhiram0106.eventplannertask.feature_home.domain.HomeRepository

interface HomeModule {
    val homeRepository: HomeRepository
}

class HomeModuleImpl(): HomeModule {
    override val homeRepository: HomeRepository by lazy {
        HomeRepositoryImpl(App.appModule.eventDatabase.eventDao())
    }
}