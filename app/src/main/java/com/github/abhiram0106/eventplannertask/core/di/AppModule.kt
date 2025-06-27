package com.github.abhiram0106.eventplannertask.core.di

import android.content.Context
import com.github.abhiram0106.eventplannertask.core.data.local.DatabaseInstance
import com.github.abhiram0106.eventplannertask.core.data.local.EventDatabase

interface AppModule {
    val eventDatabase: EventDatabase
}

class AppModuleImpl(
    private val appContext: Context
) : AppModule {
    override val eventDatabase: EventDatabase by lazy {
        DatabaseInstance.getDatabase(appContext)
    }
}