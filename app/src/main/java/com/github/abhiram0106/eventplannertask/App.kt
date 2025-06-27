package com.github.abhiram0106.eventplannertask

import android.app.Application
import com.github.abhiram0106.eventplannertask.core.di.AppModule
import com.github.abhiram0106.eventplannertask.core.di.AppModuleImpl
import com.github.abhiram0106.eventplannertask.feature_home.di.HomeModule
import com.github.abhiram0106.eventplannertask.feature_home.di.HomeModuleImpl

class App(): Application() {

    companion object {
        lateinit var appModule: AppModule
        lateinit var homeModule: HomeModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(this)
        homeModule = HomeModuleImpl()
    }
}