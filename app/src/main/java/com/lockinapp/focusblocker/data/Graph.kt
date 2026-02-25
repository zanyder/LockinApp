package com.lockinapp.focusblocker.data

import android.content.Context
import androidx.room.Room
import com.lockinapp.focusblocker.data.local.AppDatabase
import com.lockinapp.focusblocker.data.repository.BlockedAppsRepository
import com.lockinapp.focusblocker.data.repository.BlockedAppsRepositoryImpl
import com.lockinapp.focusblocker.data.repository.SessionRepository
import com.lockinapp.focusblocker.data.repository.SessionRepositoryImpl

object Graph {

    private lateinit var appContext: Context

    val database: AppDatabase by lazy {
        Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "focus_blocker.db"
        ).build()
    }

    val blockedAppsRepository: BlockedAppsRepository by lazy {
        BlockedAppsRepositoryImpl(database.blockedAppDao())
    }

    val sessionRepository: SessionRepository by lazy {
        SessionRepositoryImpl(database.focusSessionDao())
    }

    fun provide(context: Context) {
        appContext = context.applicationContext
    }
}

